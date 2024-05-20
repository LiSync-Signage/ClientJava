USE lisyncDB;

/*------------------------------------- EMPRESA -------------------------------------*/

CREATE TABLE Empresa (
	idEmpresa 		INT PRIMARY KEY AUTO_INCREMENT,
	nomeFantasia 	VARCHAR(45),
	plano 			VARCHAR(45),
    
	CONSTRAINT CHK_Plano CHECK (plano IN('Basico', 'Corporativo', 'Enterprise'))
    /*Interprise -> Enterprise*/
);

INSERT INTO Empresa (nomeFantasia, plano) VALUES
	("SPTech", 'Corporativo'),
    ("Elera.", 'Basico');

/*------------------------------------- AMBIENTE -------------------------------------*/

CREATE TABLE Ambiente (
	idAmbiente 		INT PRIMARY KEY AUTO_INCREMENT,
	setor 			VARCHAR(45),
	andar 			VARCHAR(45),
	fkEmpresa 		INT,
    
CONSTRAINT fkEmpresaAmbiente FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa)
);

/*------------------------------------- USUÁRIO -------------------------------------*/

CREATE TABLE Usuario (
	idUsuario 		INT PRIMARY KEY AUTO_INCREMENT,
    /* nome -> nomeUsuario */
	nomeUsuario		VARCHAR(45),
	email 			VARCHAR(225),
	senha 			VARCHAR(45),
	fkEmpresa 		INT NOT NULL,
	fkGestor 		INT,
    
	CONSTRAINT fkEmpresa FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa),
	CONSTRAINT fkGestor FOREIGN KEY (fkGestor) REFERENCES Usuario(idUsuario)
);

INSERT INTO Usuario (nome, email, senha, fkEmpresa, fkGestor) VALUES 
	("Felipe Almeida", "felipe.almeida@sptech.school", "felipe123", 1, null),
	("Carlos Manoel", "carlos.manoel@sptech.school", "carlos123", 1, 1),
	("Marcela Lopez", "marcela.lopez@elera.io", "marcela123", 2, null),
	("José Felipe", "jose.felipe@elera.io", "jose123", 2, 1),
	("Ademiro", "admin", "admin", 1, null);
    
/*------------------------------------- TELEVISÃO -------------------------------------*/

CREATE TABLE Televisao (
	idTelevisao 	INT PRIMARY KEY AUTO_INCREMENT,
    /* nome -> nomeTelevisao */
	nomeTelevisao	VARCHAR(45), 
	taxaAtualizacao INT,
    /*hostName -> hostname*/
	hostname 		VARCHAR(80),
	fkAmbiente 		INT NOT NULL,
    
	CONSTRAINT fkAmbiente FOREIGN KEY (fkAmbiente) REFERENCES Ambiente(idAmbiente)
);

/*------------------------------------- COMPONENTE -------------------------------------*/

CREATE TABLE Componente (
	idComponente 	INT PRIMARY KEY AUTO_INCREMENT,
	modelo 			VARCHAR(225),
	identificador 	VARCHAR(225),
	tipoComponente 	VARCHAR(45),
	fkTelevisao 	INT NOT NULL,
    
	CONSTRAINT fkTv FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
);

/*------------------------------------- JANELA -------------------------------------*/

CREATE TABLE Janela (
	idJanela 		INT PRIMARY KEY AUTO_INCREMENT,
	pidJanela		VARCHAR(45),
	titulo 			VARCHAR(225),
	localizacao 	VARCHAR(225),
	visivel 		VARCHAR(45),
	fkTelevisao 	INT,
    
	CONSTRAINT fkTelevisaoJanela FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
);

/*------------------------------------- LOG PROCESSO -------------------------------------*/

CREATE TABLE LogProcesso (
	idLog 			INT PRIMARY KEY AUTO_INCREMENT,
	pid 			INT,
	dataHora 		VARCHAR(45),
    /*nome -> nomeProcesso*/
	nomeProcesso 	VARCHAR(80),
	valor 			DOUBLE,
	fkComponente 	INT NOT NULL,
    
	CONSTRAINT fkComponenteLog FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente)
);

/*------------------------------------- LOG COMPONENTE -------------------------------------*/

CREATE TABLE LogComponente (
	idLogComponente INT PRIMARY KEY AUTO_INCREMENT,
	dataHora 		VARCHAR(45),
	valor 			DOUBLE,
	fkComponente 	INT NOT NULL,
    
	CONSTRAINT fkComponenteLogComponente FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente)
);

/*------------------------------------- COMANDO -------------------------------------*/

CREATE TABLE Comando (
	idComando 		INT PRIMARY KEY AUTO_INCREMENT,
    /*nome -> nomeComando*/
	nomeComando 	VARCHAR(45),
	fkTelevisao		INT,
    
	CONSTRAINT fkTelevisaoComando FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
);

/*------------------------------------- SELECTS -------------------------------------*/

SELECT * FROM Janela;
SELECT * FROM log;
SELECT * FROM LogComponente;
SELECT * FROM Componente;
SELECT * FROM Televisao;
SELECT * FROM ambiente;
SELECT * FROM Empresa;
SELECT * FROM Usuario;
SELECT * FROM comando;