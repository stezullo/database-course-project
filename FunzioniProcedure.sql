CREATE OR REPLACE FUNCTION pedaggio_veicolo(id_veicolo IN INTEGER)
RETURN VARCHAR
IS
  pedaggio_fin VEICOLO.PEDAGGIOFINALE%TYPE;
  classe_pedaggio VEICOLO.CLASSEPEDAGGIO%TYPE;
BEGIN
     DBMS_OUTPUT.ENABLE;
     pedaggio_fin := 0;
     IF(id_veicolo IS NULL) THEN
       RAISE_APPLICATION_ERROR(-20016, 'L''id del veicolo inserito non esiste.');
     END IF;
     FOR I IN (SELECT IdCasello FROM CHECKPOINTTAPPAVIAGGIO WHERE IdVeicolo = id_veicolo)
     LOOP
        FOR J IN (SELECT Pedaggio FROM CASELLO WHERE IDENTIFICATIVO = I.IdCasello)
        LOOP
          pedaggio_fin := pedaggio_fin + J.Pedaggio;
        END LOOP;
     END LOOP;
     IF(pedaggio_fin = 0) THEN
      RAISE_APPLICATION_ERROR(-20017, 'L''id del veicolo inserito non esiste non e'' collegato a nessun casello. Pedaggio non calcolato');
     END IF;
     -- Applicazione del Bonus di Classe del Veicolo
         -- Abbiamo deciso di adottare un bonus del 5% per i veicoli di classe A
         -- 8% per B, 15% per 3 assi, 18% per 4 assi, 20% per 5 assi.
     SELECT ClassePedaggio INTO classe_pedaggio FROM VEICOLO WHERE IDENTIFICATIVO = id_veicolo;
     IF(classe_pedaggio = 'A') THEN
        pedaggio_fin := pedaggio_fin + (pedaggio_fin*5)/100;
     ELSIF(classe_pedaggio = 'B') THEN
        pedaggio_fin := pedaggio_fin + (pedaggio_fin*8)/100;
     ELSIF(classe_pedaggio = '3') THEN
        pedaggio_fin := pedaggio_fin + (pedaggio_fin*15)/100;
     ELSIF(classe_pedaggio = '4') THEN
        pedaggio_fin := pedaggio_fin + (pedaggio_fin*18)/100;
     ELSE                                                           //classe_pedaggio = '5'
        pedaggio_fin := pedaggio_fin + (pedaggio_fin*20)/100;
     END IF;
     -- Update di Veicolo con il nuovo pedaggio totale
     UPDATE VEICOLO 
        SET PedaggioFinale = pedaggio_fin
        WHERE IDENTIFICATIVO = id_veicolo;
    RETURN 'Il pedaggio finale del veicolo '||id_veicolo||' e'' di '||pedaggio_fin||' €';
END;
/
CREATE OR REPLACE PROCEDURE percorso_veicolo(id_veicolo Veicolo.Identificativo%TYPE)
IS 
  n_autostrada VARCHAR2(50);
  dir_name     VARCHAR2(50);
  n_casello    VARCHAR2(50);
  pedaggio_casello FLOAT;
  tempo_evento DATE;
  luogo_veicolo VARCHAR2(50);
  id_svincolo INTEGER;
  pos_veicolo FLOAT;
BEGIN
  --IF((SELECT * FROM CHECKPOINTTAPPAVIAGGIO WHERE IdVeicolo = id_veicolo) IS NULL) THEN 
  --    RAISE_APPLICATION_ERROR(-20020,'Id Veicolo non esiste in CheckPointTappaViaggio');
  --END IF;
  FOR I IN (SELECT * FROM CHECKPOINTTAPPAVIAGGIO WHERE IdVeicolo = id_veicolo)
  LOOP
    id_svincolo := I.IdSvincolo;
    luogo_veicolo := I.Luogo;
    pos_veicolo := I.PosizioneVeicolo;
    tempo_evento := I.TempoEvento;
    IF(I.IdSvincolo IS NOT NULL) THEN                  -- Il veicolo ha attraversato uno svincolo.
        SELECT NOME INTO n_autostrada FROM AUTOSTRADA WHERE IDENTIFICATIVO = (SELECT AutostradaEntrante FROM SVINCOLO WHERE IDENTIFICATIVO = I.IDSVINCOLO);
        
        SELECT NOMEDIREZIONE INTO dir_name FROM DIREZIONETRATTA WHERE IDENTIFICATIVO = 
                    (SELECT IdDirezioneAutostradaEntrante FROM SVINCOLO WHERE IDENTIFICATIVO = I.IDSVINCOLO);
        INSERT INTO tempPercorso VALUES(seqPercorso.NEXTVAL,id_veicolo,'Il veicolo in luogo '||I.Luogo||' al km '||I.PosizioneVeicolo||'  con id '||I.IdVeicolo||' attraversa lo svincolo con id '||I.IdSvincolo||
               ' ed entra in '||n_autostrada||' con direzione '||dir_name||' al tempo '||I.TempoEvento||'.');
    ELSIF(I.IdCasello IS NOT NULL) THEN                -- Il veicolo ha attraversato un  casello.
      SELECT NOME INTO n_casello FROM CASELLO WHERE IDENTIFICATIVO = I.IDCASELLO;
      SELECT NOME INTO n_autostrada FROM AUTOSTRADA WHERE IDENTIFICATIVO = (SELECT IdAutostrada FROM CASELLO WHERE IDENTIFICATIVO = I.IDCASELLO);
      SELECT Pedaggio INTO pedaggio_casello FROM CASELLO WHERE IDENTIFICATIVO = I.IDCASELLO;
      INSERT INTO tempPercorso VALUES(seqPercorso.NEXTVAL,id_veicolo,'Il veicolo in luogo '||I.Luogo||' al km '||I.PosizioneVeicolo||'  con id '||I.IdVeicolo||' attraversa il casello di '||n_casello||
               ' in Autostrada '||n_autostrada||' e paga '||pedaggio_casello||'€ al tempo '||I.TempoEvento||'.');
    ELSIF(I.IdControlloElettronico IS NOT NULL) THEN   -- Il veicolo ha attraversato un controllo elettronico, controlla la velocita mi raccomando.
     SELECT NOME INTO n_autostrada FROM AUTOSTRADA WHERE IDENTIFICATIVO = (SELECT IdAutostrada FROM DIREZIONETRATTA WHERE IDENTIFICATIVO = (SELECT IdDirezione FROM CONTROLLOELETTRONICO WHERE IDENTIFICATIVO = I.IDControlloElettronico));
      INSERT INTO tempPercorso VALUES(seqPercorso.NEXTVAL,id_veicolo,'Il veicolo in luogo '||I.Luogo||' al km '||I.PosizioneVeicolo||'  con id '||I.IdVeicolo||' passa sotto il controllo con id '||I.IdControlloElettronico||
               ' in Autostrada '||n_autostrada||' a '||I.VelocitaIstantaneaVeicolo||'km/h al tempo '||I.TempoEvento||'.');
    END IF;
  END LOOP;
  EXCEPTION
      WHEN NO_DATA_FOUND THEN
          INSERT INTO tempPercorso VALUES(seqPercorso.NEXTVAL,id_veicolo,'Il veicolo in luogo '||luogo_veicolo||' al km '||pos_veicolo||'  con id '||id_veicolo||' attraversa lo svincolo con id '||id_svincolo||
               ' ed esce dall''autostrada al tempo '||tempo_evento||'.');
END;
/
CREATE OR REPLACE FUNCTION CASELLI_COLLEGATI (CASELLO1 INTEGER, CASELLO2 INTEGER) // Funzione per determinare il collegamento di due caselli.
RETURN VARCHAR
IS 
   RES VARCHAR2(100);
   ID1 INTEGER;
   ID2 INTEGER;
BEGIN
  RES := NULL;
  SELECT IDAUTOSTRADA INTO ID1 FROM CASELLO WHERE IDENTIFICATIVO = CASELLO1;
  SELECT IDAUTOSTRADA INTO ID2 FROM CASELLO WHERE IDENTIFICATIVO=CASELLO2;
  IF (ID1=ID2) THEN
      RETURN 'I CASELLI SONO SULLA STESSA AUTOSTRADA. SONO COLLEGATI';
  ELSIF (ID1 <> ID2) THEN
    FOR CURR1 IN (SELECT IDENTIFICATIVO,AUTOSTRADAENTRANTE FROM SVINCOLO WHERE AUTOSTRADAUSCENTE=ID1)
    LOOP
            IF(CURR1.AUTOSTRADAENTRANTE=ID2) THEN
                RETURN 'I DUE CASELLI SONO COLLEGATI DALLO SVINCOLO con ID '||CURR1.IDENTIFICATIVO||'';
            END IF;
    END LOOP;
    FOR CURR1 IN (SELECT IDENTIFICATIVO,AUTOSTRADAENTRANTE FROM SVINCOLO WHERE AUTOSTRADAUSCENTE=ID1)
    LOOP
            FOR CURR2 IN (SELECT IDENTIFICATIVO,AUTOSTRADAUSCENTE FROM SVINCOLO WHERE AUTOSTRADAENTRANTE=ID2)
            LOOP
                IF (CURR1.AUTOSTRADAENTRANTE = CURR2.AUTOSTRADAUSCENTE) THEN
                      RETURN 'I CASELLI SONO COLLEGATI DAGLI SVINCOLI con ID '||CURR1.IDENTIFICATIVO||'  E  ID '|| CURR2.IDENTIFICATIVO||'';
                END IF;
            END LOOP;
    END LOOP;
  END IF;
  RETURN 'I DUE CASELLI NON SONO COLLEGATI';
END;
/
COMMIT;