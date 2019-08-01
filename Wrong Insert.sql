--INSERT CHE SCATENANO I TRIGGER

INSERT INTO Svincolo VALUES  (seqSvincolo.NEXTVAL, 10, 1, 2, 8);//prova ad inserire uno svincolo da autostrada 1 e autostrada 2 con un id direzione appartenente all'autostrada con id 4
/
INSERT INTO Casello  VALUES (seqCasello.NEXTVAL , 'Napoli' , 900 , 1 , 1 );//prova ad inserire un casello fuori dalla lunghezza dell'autostrada
/
INSERT INTO Svincolo VALUES  (seqSvincolo.NEXTVAL, 950, 1, 2, 3);//prova ad inserire uno svincolo fuori la lunghezza dell'autostrada
/
INSERT INTO Area VALUES (seqArea.NEXTVAL, 900.8, 2);//prova ad inserire un area fuori la lunghezza dell'autostrada
/
INSERT INTO ControlloElettronico VALUES (seqControlloElettronico.NEXTVAL,   900, 130, 'Tutor', 1);//prova ad inserire un controllo elettronico fuori la lunghezza dell'autostrada
/
INSERT INTO Servizio VALUES (seqServizio.NEXTVAL, 'T', 'T' , 'T', 'T', 'T' , 'T', 'T', 'F', 25);//prova a collegare un area di servizio all'id di un area giˆ assegnata per una piazzola di sosta
/
INSERT INTO Parcheggio VALUES  (seqParcheggio.NEXTVAL, 'F', 26);//prova a collegare una piazzola di sosta ad un area già assegnata ad un servizio
/
INSERT INTO DirezioneTratta VALUES (seqDirezioneTratta.NEXTVAL, 'Errore Direzione', 1);//prova ad inserire la terza direzione all'autostrada con id 1
/
INSERT INTO InformazioneLive VALUES (1, 6, 5, 'Incidente', SYSDATE, 1);//prova ad inserire un informazione live con luogo di inizio successivo a quello di fine
/
INSERT INTO CheckPointTappaViaggio VALUES (seqCheckPointTappaViaggio.NEXTVAL, 900, 130, 'Linate', SYSDATE, 2, NULL,  NULL, 23);//prova ad inserire un record con posizione veicolo oltre la lunghezza dell'autostrada
/
INSERT INTO CheckPointTappaViaggio VALUES (seqCheckPointTappaViaggio.NEXTVAL, 1, 130, 'Linate', SYSDATE, 2, 2,  NULL, 23);//prova ad inserire un record indicando che una vettura passa contemporaneamente per uno svincolo e per casello
/
INSERT INTO CheckPointTappaViaggio VALUES (seqCheckPointTappaViaggio.NEXTVAL, 1, 130, 'Linate', SYSDATE, 2, NULL,  NULL, NULL);//prova ad inserire un record senza indicare neanche un id tra casello svincolo e controlloelettronico
/
INSERT INTO Incidente VALUES (2, 'INCIDENTE1', 6, 6, 5); //inserisce un incidente tra 2 veicoli con stesso id
/
COMMIT;
