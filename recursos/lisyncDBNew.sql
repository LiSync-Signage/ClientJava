	CREATE DATABASE lisyncDB;
	USE lisyncDB;


	CREATE TABLE Empresa (
		idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
		nomeFantasia VARCHAR(45),
		plano VARCHAR(45),
		CONSTRAINT CHK_Plano CHECK (plano IN('Basico', 'Corporativo', 'Interprise'))
	);
	INSERT INTO Empresa (nomeFantasia, plano) VALUES ("SP Tech", 'Corporativo'), ("Elera.", 'Basico');

	CREATE TABLE ambiente (
		idAmbiente INT PRIMARY KEY AUTO_INCREMENT,
		setor VARCHAR(45),
		andar varchar(45),
		fkEmpresa INT,
		CONSTRAINT fkEmpresaAmbiente FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa)
	);

	CREATE TABLE Usuario (
		idUsuario INT PRIMARY KEY AUTO_INCREMENT,
		nome VARCHAR(45),
		email VARCHAR(225),
		senha VARCHAR(45),
		fkEmpresa INT NOT NULL,
		fkGestor INT,
		CONSTRAINT fkEmpresa FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa),
		CONSTRAINT fkGestor FOREIGN KEY (fkGestor) REFERENCES Usuario(idUsuario)
	);
	INSERT INTO Usuario (nome, email, senha, fkEmpresa, fkGestor) VALUES 
		("Felipe Almeida", "felipe.almeida@sptech.school", "felipe123", 1, null),
		("Carlos Manoel", "carlos.manoel@sptech.school", "carlos123", 1, 1),
		("Marcela Lopez", "marcela.lopez@elera.io", "marcela123", 2, null),
		("José Felipe", "jose.felipe@elera.io", "jose123", 2, 1),
		("Ademiro", "admin", "admin", 1, null);


	CREATE TABLE Televisao (
		idTelevisao INT PRIMARY KEY AUTO_INCREMENT,
		nome VARCHAR(45), 
		taxaAtualizacao INT,
		hostName VARCHAR(80),
		fkAmbiente INT NOT NULL,
		CONSTRAINT fkAmbiente FOREIGN KEY (fkAmbiente) REFERENCES ambiente(idAmbiente)
	);

	CREATE TABLE Componente (
		idComponente INT PRIMARY KEY AUTO_INCREMENT,
		modelo VARCHAR(225),
		identificador VARCHAR(225),
		tipoComponente VARCHAR(45),
		fkTelevisao INT NOT NULL,
		CONSTRAINT fkTv FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);

	CREATE TABLE Janela (
		idJanela INT PRIMARY KEY AUTO_INCREMENT,
		pidJanela VARCHAR(45),
		titulo VARCHAR(225),
		localizacao VARCHAR(225),
		visivel VARCHAR(45),
		fkTelevisao INT,
		CONSTRAINT fkTelevisaoJanela FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);

	CREATE TABLE Log (
		idLog INT PRIMARY KEY AUTO_INCREMENT,
		pid INT,
		dataHora VARCHAR(45),
		nomeProcesso VARCHAR(80),
		valor DOUBLE,
		fkComponente INT NOT NULL,
		CONSTRAINT fkComponenteLog FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente)
	);

	CREATE TABLE LogComponente (
		idLogComponente INT PRIMARY KEY AUTO_INCREMENT,
		dataHora VARCHAR(45),
		valor DOUBLE,
		fkComponente INT NOT NULL,
		CONSTRAINT fkComponenteLogComponente FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente)
	);

	CREATE TABLE comando (
		idComando INT PRIMARY KEY AUTO_INCREMENT,
		nome VARCHAR(45),
		fkTelevisao INT,
		CONSTRAINT fkTelevisaoComando FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);

	CREATE TABLE Alertas (
		idAlertas INT PRIMARY KEY AUTO_INCREMENT,
		valor DOUBLE,
		datahora VARCHAR(45),
		fkTelevisao INT,
		CONSTRAINT fkTelevisaoAlertas FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);


	select *from janela;

	select *from log;

	select *from logComponente;

	select *from componente;

	select *from televisao;

	select *from ambiente;

	select *from Usuario;
    
    select *from comando;










-- DROP DATABASE lisyncDB;