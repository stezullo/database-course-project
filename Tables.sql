CREATE TABLE Autostrada(
	Identificativo INTEGER PRIMARY KEY,
	Nome VARCHAR2(50) UNIQUE,
	AltraDenominazione VARCHAR2(50) NOT NULL,
	Inizio VARCHAR2(50) NOT NULL,
	Fine VARCHAR2(50) NOT NULL,
	LunghezzaTratta FLOAT NOT NULL
);
/
CREATE TABLE Casello (
	Identificativo INTEGER PRIMARY KEY,
	Nome VARCHAR2(20) NOT NULL UNIQUE,
	PosizioneCasello FLOAT,
	Pedaggio FLOAT,
	IdAutostrada INTEGER NOT NULL,
	CONSTRAINT ca_IdAutostrada FOREIGN KEY (IdAutostrada)
        REFERENCES Autostrada(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE DirezioneTratta(
	Identificativo INTEGER PRIMARY KEY,
	NomeDirezione VARCHAR2(50) NOT NULL,
	IdAutostrada INTEGER NOT NULL,
	CONSTRAINT dir_IdAutostrada FOREIGN KEY(IdAutostrada) 
	REFERENCES Autostrada(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE Svincolo(
	Identificativo INTEGER PRIMARY KEY,
	PosizioneSvincolo FLOAT DEFAULT 0,
	AutostradaUscente INTEGER,
	AutostradaEntrante INTEGER,
	IdDirezioneAutostradaEntrante INTEGER,
	CONSTRAINT sv_AutostradaUscente FOREIGN KEY (AutostradaUscente)
        REFERENCES Autostrada(Identificativo) ON DELETE CASCADE,
	CONSTRAINT sv_AutostradaEntrante FOREIGN KEY (AutostradaEntrante) 
	REFERENCES Autostrada(Identificativo) ON DELETE CASCADE,
	CONSTRAINT sv_IdDirezioneTratta FOREIGN KEY (IdDirezioneAutostradaEntrante)
        REFERENCES DirezioneTratta(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE Area(
	Identificativo INTEGER PRIMARY KEY,
	PosizioneArea FLOAT DEFAULT 0,
	IdDirezioneTratta INTEGER NOT NULL,
	CONSTRAINT ar_IdDirezioneTratta FOREIGN KEY(IdDirezioneTratta) 
	REFERENCES DirezioneTratta(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE Servizio(
	Identificativo INTEGER PRIMARY KEY,
	GPL CHAR(1) DEFAULT 'F' CHECK (GPL IN('T','F')),
	Metano CHAR(1) DEFAULT 'F' CHECK (Metano IN('T','F')),
	WiFi CHAR(1) DEFAULT 'F' CHECK (WiFi IN('T','F')),
	Area_Camper CHAR(1) DEFAULT 'F' CHECK (Area_Camper IN('T','F')),
	HiPoint CHAR(1) DEFAULT 'F' CHECK (HiPoint IN('T','F')),
	Bancomat CHAR(1) DEFAULT 'F' CHECK (Bancomat IN('T','F')),
	Idrogeno CHAR(1) DEFAULT 'F' CHECK (Idrogeno IN('T','F')),
	Elettrica CHAR(1) DEFAULT 'F' CHECK (Elettrica IN('T','F')),
	IdArea INTEGER,
	CONSTRAINT se_IdArea FOREIGN KEY (IdArea) REFERENCES Area(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE Parcheggio(
	Identificativo INTEGER PRIMARY KEY,
	SOS CHAR(1) DEFAULT 'F' CHECK(SOS IN ('T','F')),
	IdArea INTEGER,
	CONSTRAINT park_IdArea FOREIGN KEY(IdArea) 
	REFERENCES Area(Identificativo) ON DELETE CASCADE	
);
/
CREATE TABLE Veicolo(
	Identificativo INTEGER PRIMARY KEY,
	Targa VARCHAR2(7) UNIQUE,
	Marca VARCHAR2(30),
	Modello VARCHAR2(50),
	Cilindrata INTEGER,
	ClassePedaggio CHAR(1) DEFAULT 'B' NOT NULL CHECK(ClassePedaggio IN ('A','B','3','4','5')),
	PedaggioFinale FLOAT DEFAULT 0
);
/
CREATE TABLE ControlloElettronico(
	Identificativo INTEGER PRIMARY KEY,
	KM FLOAT,
	VelocitaMassima FLOAT,
	Tipologia VARCHAR2(20) CHECK(Tipologia IN ('Autovelox','Tutor','Vergilius')),
	IdDirezione INTEGER NOT NULL,
	CONSTRAINT ce_IdDirezione FOREIGN KEY (IdDirezione) REFERENCES DirezioneTratta(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE ControlloVelocita(
	Identificativo INTEGER PRIMARY KEY,
	Multa CHAR(1) DEFAULT 'F' CHECK(Multa IN ('T','F')),
	IdVeicolo INTEGER NOT NULL,
	IdCElettronico INTEGER NOT NULL,
	CONSTRAINT cv_IdVeicolo FOREIGN KEY(IdVeicolo) 
	REFERENCES Veicolo(Identificativo) ON DELETE CASCADE,
	CONSTRAINT cv_IdCElettronico FOREIGN KEY(IdCElettronico) 
	REFERENCES ControlloElettronico(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE InformazioneLive(
	Identificativo INTEGER PRIMARY KEY,
	InizioPosizioneEvento FLOAT DEFAULT 0,
	FinePosizioneEvento FLOAT DEFAULT 0,
	InfoAggiuntive VARCHAR2(100),
	TempoEvento TIMESTAMP,
	IdDirezioneTratta INTEGER NOT NULL,
	CONSTRAINT live_IdDirezioneTratta FOREIGN KEY(IdDirezioneTratta) 
	REFERENCES DirezioneTratta(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE Incidente(
	Identificativo INTEGER PRIMARY KEY,
	CodiceIncidente VARCHAR2(20),
	IdVeicolo1 INTEGER,
	IdVeicolo2 INTEGER,
	IdInfoLive INTEGER,
	CONSTRAINT in_IdVeicolo1 FOREIGN KEY (IdVeicolo1) REFERENCES Veicolo(Identificativo) ON DELETE CASCADE,
	CONSTRAINT in_IdVeicolo2 FOREIGN KEY (IdVeicolo2) REFERENCES Veicolo(Identificativo) ON DELETE CASCADE,
	CONSTRAINT in_IdInfoLive FOREIGN KEY (IdInfoLive) REFERENCES InformazioneLive(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE Rallentamento(
	Identificativo INTEGER PRIMARY KEY,
	IdInfoLive INTEGER,
	CONSTRAINT ral_IdInfoLive FOREIGN KEY(IdInfoLive) 
	REFERENCES InformazioneLive(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE LavoroInCorso(
	Identificativo INTEGER PRIMARY KEY,
	IdInfoLive INTEGER,
	CONSTRAINT lav_IdInfoLive FOREIGN KEY(IdInfoLive) 
	REFERENCES InformazioneLive(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE CheckpointTappaViaggio(
	Identificativo INTEGER PRIMARY KEY,
	PosizioneVeicolo FLOAT DEFAULT 0,
	VelocitaIstantaneaVeicolo FLOAT DEFAULT 0,
	Luogo VARCHAR2(50),
	TempoEvento TIMESTAMP,
	IdVeicolo INTEGER NOT NULL,
	IdCasello INTEGER,
	IdControlloElettronico INTEGER,
	IdSvincolo INTEGER,
	CONSTRAINT chk_IdVeicolo FOREIGN KEY(IdVeicolo) 
	REFERENCES Veicolo(Identificativo) ON DELETE CASCADE,
	CONSTRAINT chk_IdCasello FOREIGN KEY(IdCasello) 
	REFERENCES Casello(Identificativo) ON DELETE CASCADE,
	CONSTRAINT chk_IdControlloElettronico FOREIGN KEY(IdControlloElettronico) 
	REFERENCES ControlloElettronico(Identificativo) ON DELETE CASCADE,
	CONSTRAINT chk_IdSvincolo FOREIGN KEY(IdSvincolo) 
	REFERENCES Svincolo(Identificativo) ON DELETE CASCADE
);
/
CREATE TABLE tempPercorso
   (
    IdPercorso INTEGER PRIMARY KEY,
    IdVeicolo INTEGER NOT NULL,
    AzioneVeicolo VARCHAR2(200),
    CONSTRAINT tp_IdVeicolo FOREIGN KEY(IdVeicolo) REFERENCES VEICOLO(Identificativo) ON DELETE CASCADE
);
/
COMMIT;
