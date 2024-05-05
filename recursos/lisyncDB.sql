CREATE DATABASE lisyncDB;
USE lisyncDB;

CREATE TABLE Empresa(
	idEmpresa INT PRIMARY KEY auto_increment,
    nomeFantasia VARCHAR(45),
    plano VARCHAR(45),
    CONSTRAINT CHK_Plano CHECK (plano IN('Basico', 'Corporativo', 'Interprise'))
);

CREATE TABLE Usuario(
	idUsuario INT PRIMARY KEY auto_increment,
    nome VARCHAR(45),
    email VARCHAR(225),
    senha VARCHAR(45),
    fkEmpresa int,
    fkGestor int,
    constraint fkEmpresa foreign key (fkEmpresa) references Empresa(idEmpresa),
    constraint fkGestor foreign key (fkGestor) references Usuario(idUsuario)
);

INSERT INTO Empresa (nomeFantasia, plano) VALUES ("SP Tech", 'Corporativo'), ("Elera.", 'Basico');

INSERT INTO Usuario (nome, email, senha, fkEmpresa, fkGestor) VALUES 
	("Felipe Almeida", "felipe.almeida@sptech.school", "felipe123", 1, null),
	("Carlos Manoel", "carlos.manoel@sptech.school", "carlos123", 1, 1),
    ("Marcela Lopez", "marcela.lopez@elera.io", "marcela123", 2, null),
	("José Felipe", "jose.felipe@elera.io", "jose123", 2, 1);

CREATE TABLE Televisao(
	idTelevisao INT PRIMARY KEY auto_increment,
    andar CHAR(3),
    setor VARCHAR(225),
    nome VARCHAR(45), 
    taxaAtualizacao INT,
    hostName VARCHAR(80),
    fkEmpresa INT,
    constraint fkEmpresaTv foreign key (fkEmpresa) references Empresa(idEmpresa)
);

CREATE TABLE Componente (
	idComponente INT PRIMARY KEY auto_increment,
    modelo VARCHAR(225),
    identificador VARCHAR(225),
	tipoComponente VARCHAR(45),
    fkTelevisao INT,
    constraint fkTv foreign key (fkTelevisao) references Televisao(idTelevisao)
);

CREATE TABLE Janela (
	idJanela INT PRIMARY KEY auto_increment,
    pidJanela VARCHAR(45),
    comando VARCHAR(225),
    titulo VARCHAR(225),
    localizacao VARCHAR(225),
    visivel VARCHAR(45),
    dataHora VARCHAR(45),
    fkTelevisao int,
    constraint fkTelevisao foreign key (fkTelevisao) references Televisao(idTelevisao)
);

CREATE TABLE Processo (
	idProcesso INT PRIMARY KEY auto_increment,
    pid VARCHAR(45),
    nome VARCHAR(45),
    dataHora VARCHAR(45),
    fkTelevisao int,
    foreign key (fkTelevisao) references Televisao(idTelevisao)
);

CREATE TABLE Log (
	idLog INT PRIMARY KEY auto_increment,
    dataHora VARCHAR(45),
    valor DOUBLE
);

CREATE TABLE LogComponente (
	idLogComponente INT PRIMARY KEY auto_increment,
	dataHora VARCHAR(45),
    valor DOUBLE,
    fkComponente int,
    foreign key (fkComponente) references Componente(idComponente)
);


-- DROP DATABASE lisyncDB;