CREATE OR REPLACE TRIGGER dir_inclusa_autostrada  /* TRIGGER CHE VERIFICA CHE OGNI IDDIREZIONEAUTOSTRADAENTRANTE DEVE APPARTENERE ALL'AUTOSTRADA ENTRANTE*/
BEFORE INSERT ON SVINCOLO
FOR EACH ROW
DECLARE
  IDDIR DIREZIONETRATTA.IDENTIFICATIVO%TYPE;
BEGIN
  DBMS_OUTPUT.enable;
  
  SELECT Identificativo INTO IDDIR FROM DirezioneTratta WHERE IdAutostrada = :NEW.AutostradaEntrante AND Identificativo = :NEW.IdDirezioneAutostradaEntrante;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
          RAISE_APPLICATION_ERROR(-20000, 'Non è stato possibile inserire lo svincolo poichè l''IdDirezioneAutostradaEntrante immessa non è collegata all''AutostradaEntrante.');
END;						
/
CREATE OR REPLACE TRIGGER pos_cas_lung_autostrada /* TRIGGER CHE CONTROLLA CHE LA POSIZIONE DEL CASELLO E' NEI LIMITI */
BEFORE INSERT ON CASELLO
FOR EACH ROW
DECLARE
  appoggio Autostrada.LunghezzaTratta%TYPE;
BEGIN
  DBMS_OUTPUT.ENABLE;
  SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA WHERE Identificativo = :NEW.IdAutostrada;
  IF(:NEW.PosizioneCasello < 0 OR :NEW.PosizioneCasello > appoggio) THEN
    RAISE_APPLICATION_ERROR(-20001,'La posizione del casello deve stare all''interno dei limiti');
  END IF;
END; 
/
CREATE OR REPLACE TRIGGER pos_sv_lung_autostrada /* TRIGGER CHE CONTROLLA CHE LA POSIZIONE DELLO SVINCOLO E' NEI LIMITI */
BEFORE INSERT ON SVINCOLO
FOR EACH ROW
DECLARE
  appoggio Autostrada.LunghezzaTratta%TYPE;
BEGIN
  SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA WHERE Identificativo = :NEW.AutostradaUscente;
  IF(:NEW.PosizioneSvincolo < 0 OR :NEW.PosizioneSvincolo > appoggio) THEN
    RAISE_APPLICATION_ERROR(-20002,'La posizione dello svincolo deve stare all''interno dei limiti');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER pos_aree_lung_autostrada /* TRIGGER CHE CONTROLLA CHE LA POSIZIONE DELL'AREA (DI SERVIZIO O PARCHEGGIO E' NEI LIMITI) */
BEFORE INSERT ON AREA
FOR EACH ROW
DECLARE
  appoggio Autostrada.LunghezzaTratta%TYPE;
BEGIN
  SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA NATURAL JOIN DIREZIONETRATTA WHERE Identificativo = :NEW.IdDirezioneTratta; 
  IF(:NEW.PosizioneArea < 0 OR :NEW.PosizioneArea > appoggio) THEN
    RAISE_APPLICATION_ERROR(-20003,'La posizione dell''area deve stare all''interno dei limiti');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER pos_celettr_lung_autostrada /* TRIGGER CHE CONTROLLA CHE LA POSIZIONE DEL CONTROLLO ELETTRONICO E' NEI LIMITI */
BEFORE INSERT ON CONTROLLOELETTRONICO
FOR EACH ROW
DECLARE
  appoggio Autostrada.LunghezzaTratta%TYPE;
BEGIN
  SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA NATURAL JOIN DIREZIONETRATTA WHERE Identificativo = :NEW.IdDirezione; 
  IF(:NEW.KM < 0 OR :NEW.KM > appoggio) THEN
    RAISE_APPLICATION_ERROR(-20004,'La posizione del controllo elettronico deve stare all''interno dei limiti');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER controlla_servizio /* TRIGGER CHE CONTROLLA SE UN AREA E' DI PARCHEGGIO NON PUO' ESSERE DI SERVIZIO */
BEFORE INSERT ON SERVIZIO
FOR EACH ROW
DECLARE
  appoggio PARCHEGGIO.Identificativo%TYPE;
BEGIN
  SELECT Identificativo INTO appoggio FROM PARCHEGGIO WHERE IdArea = :NEW.IdArea;
  IF(appoggio IS NOT NULL) THEN
   RAISE_APPLICATION_ERROR(-20005,'L''IdArea inserito e'' gia'' collegato ad un''area di parcheggio.');
  END IF;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('');
END;
/
CREATE OR REPLACE TRIGGER controlla_parcheggio /* TRIGGER CHE CONTROLLA SE UN AREA E' DI SERVIZIO NON PUO' ESSERE DI PARCHEGGIO */
BEFORE INSERT ON PARCHEGGIO
FOR EACH ROW
DECLARE
  appoggio SERVIZIO.Identificativo%TYPE;
BEGIN
  SELECT Identificativo INTO appoggio FROM SERVIZIO WHERE IdArea = :NEW.IdArea;
  IF(appoggio IS NOT NULL) THEN
   RAISE_APPLICATION_ERROR(-20006,'L''IdArea inserito e'' gia'' collegato ad un''area di servizio.');
  END IF;
  EXCEPTION
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('');
END;
/
CREATE OR REPLACE TRIGGER max_direzioni /* TRIGGER CHE CONTROLLA SE UN'AUTOSTRADA HA AL MASSIMO 2 DIREZIONI */
BEFORE INSERT ON DIREZIONETRATTA
FOR EACH ROW
DECLARE
  cont INTEGER;
BEGIN
  SELECT COUNT(Identificativo) INTO cont FROM DIREZIONETRATTA WHERE IdAutostrada = :NEW.IdAutostrada;
  IF(cont = 2) THEN
   RAISE_APPLICATION_ERROR(-20007,'Non e'' possibile inserire ulteriori direzioni.');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER inizio_min_fine_evento /* TRIGGER CHE CONTROLLA SE IL KM DELL'INIZIO DELL'EVENTO SIA SEMPRE MINORE O UGUALE DELLA FINE */
BEFORE INSERT ON INFORMAZIONELIVE
FOR EACH ROW
BEGIN
  IF (:NEW.InizioPosizioneEvento > :NEW.FinePosizioneEvento) THEN
    RAISE_APPLICATION_ERROR(-20008,'KM dell''Inizio dell''evento deve essere minore o uguale della fine.');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER pos_veicolo_in_autostrada /* TRIGGER CHE CONTROLLA CHE LA POSIZIONE DEL VEICOLO SIA NEI LIMITI */
BEFORE INSERT ON CHECKPOINTTAPPAVIAGGIO
FOR EACH ROW
DECLARE
  appoggio AUTOSTRADA.LunghezzaTratta%TYPE;
BEGIN
  IF(:NEW.IdCasello IS NOT NULL) THEN
    SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA WHERE Identificativo = (SELECT IdAutostrada FROM CASELLO WHERE Identificativo = :NEW.IdCasello);
  ELSIF(:NEW.IdSvincolo IS NOT NULL) THEN
    SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA WHERE Identificativo = (SELECT AutostradaUscente FROM SVINCOLO WHERE Identificativo = :NEW.IdSvincolo);  
  ELSIF(:NEW.IdControlloElettronico IS NOT NULL) THEN
    SELECT LunghezzaTratta INTO appoggio FROM AUTOSTRADA WHERE Identificativo = (SELECT IdAutostrada FROM DIREZIONETRATTA WHERE Identificativo =
                  (SELECT IdDirezione FROM CONTROLLOELETTRONICO WHERE Identificativo = :NEW.IdControlloElettronico)); 
  END IF;
  IF(:NEW.PosizioneVeicolo < 0 OR :NEW.PosizioneVeicolo > appoggio) THEN
    RAISE_APPLICATION_ERROR(-20009,'La posizione del veicolo deve essere all''interno dei limiti');
  END IF;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('');
END;
/
CREATE OR REPLACE TRIGGER unico_oggetto_da_attr /* TRIGGER CHE CONTROLLA CHE SE SI PASSA PER UN CASELLO , NON SI PUO' PASSARE CONTEMPORANEAMENTE PER UNO SVINCOLO, etc.. E che sia impossibile che sia IdSvincolo, IdCasello e IdControllo siano NULL contemporaneamente */
BEFORE INSERT ON CHECKPOINTTAPPAVIAGGIO
FOR EACH ROW
BEGIN
  IF    (:NEW.IdCasello IS NULL AND :NEW.IdControlloElettronico IS NULL AND :NEW.IdSvincolo IS NULL) THEN
    RAISE_APPLICATION_ERROR(-20010,'Non Ë possibile che siano vuoti contemporaneamente sia IdCasello, IdControlloElettronico e IdSvincolo.');  
  ELSIF (:NEW.IdCasello IS NOT NULL AND (:NEW.IdControlloElettronico IS NOT NULL OR :NEW.IdSvincolo IS NOT NULL)) THEN
    RAISE_APPLICATION_ERROR(-20010,'Non Ë possibile attraversare contemporaneamente piu'' oggetti tra : caselli,controlli e svincoli.');
  ELSIF (:NEW.IdControlloElettronico IS NOT NULL AND :NEW.IdSvincolo IS NOT NULL) THEN 
    RAISE_APPLICATION_ERROR(-20010,'Non Ë possibile attraversare contemporaneamente piu'' oggetti tra : caselli,controlli e svincoli.');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER incidente_veicoli_diversi /* TRIGGER CHE CONTROLLA CHE L'ID DEI VEICOLI CHE FANNO L'INCIDENTE SIA DIVERSO */
BEFORE INSERT ON INCIDENTE
FOR EACH ROW
BEGIN
  IF(:NEW.IdVeicolo1 = :NEW.IdVeicolo2) THEN
    RAISE_APPLICATION_ERROR(-20011,'Un incidente deve avere Id veicoli diversi');
  END IF;
END;
/
CREATE OR REPLACE TRIGGER multa_veicolo
AFTER INSERT ON CHECKPOINTTAPPAVIAGGIO
FOR EACH ROW
DECLARE
  tipologia_controllo CONTROLLOELETTRONICO.Tipologia%TYPE;
  km CONTROLLOELETTRONICO.KM%TYPE;
  velmax CONTROLLOELETTRONICO.VelocitaMassima%TYPE;
BEGIN
  IF( :NEW.IdControlloElettronico IS NOT NULL) THEN
     SELECT Tipologia INTO tipologia_controllo FROM CONTROLLOELETTRONICO WHERE Identificativo = :NEW.IdControlloElettronico;
     SELECT KM INTO km FROM CONTROLLOELETTRONICO WHERE Identificativo = :NEW.IdControlloElettronico;
     SELECT VelocitaMassima INTO velmax FROM CONTROLLOELETTRONICO WHERE Identificativo = :NEW.IdControlloElettronico;
     IF( :NEW.PosizioneVeicolo = km AND(tipologia_controllo = 'Autovelox' OR tipologia_controllo = 'Vergilius') AND :NEW.VelocitaIstantaneaVeicolo > velmax) THEN
            INSERT INTO CONTROLLOVELOCITA VALUES(seqControlloElettronico.NEXTVAL,'T',:NEW.IdVeicolo,:NEW.IdControlloElettronico);
     ELSIF(:NEW.PosizioneVeicolo = km AND (tipologia_controllo = 'Autovelox' OR tipologia_controllo = 'Vergilius') AND :NEW.VelocitaIstantaneaVeicolo <= velmax) THEN
	    INSERT INTO CONTROLLOVELOCITA VALUES(seqControlloElettronico.NEXTVAL,'F',:NEW.IdVeicolo,:NEW.IdControlloElettronico);
     END IF;
  END IF;
END;
/
COMMIT;