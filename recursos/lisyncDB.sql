DROP DATABASE lisyncDB;
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
    constraint fkEmpresa foreign key (fkEmpresa) references Empresa(idEmpresa)
);

INSERT INTO Empresa (nomeFantasia, plano) VALUES ("SP Tech", 'Corporativo'), ("Elera.", 'Basico');

INSERT INTO Usuario (nome, email, senha, fkEmpresa) VALUES 
	("Matheus Shoji", "matheus.shoji@sptech.school", "matheus_shoji123", 1),
	("Gabriel Shoji", "gabriel.shoji@sptech.school", "gabriel123", 2),
	("Raquel Shoji", "raquel.shoji@sptech.school", "raquel_shoji123", null);

CREATE TABLE Televisao(
	idTelevisao INT PRIMARY KEY auto_increment,
    andar CHAR(3),
    setor VARCHAR(225),
    taxaAtualizacao INT,
    ipTv VARCHAR(45),
    sistemaOperacional VARCHAR(45),
    fkEmpresa INT,
    constraint fkEmpresaTv foreign key (fkEmpresa) references Empresa(idEmpresa)
);

    
CREATE TABLE TipoComponente (
	idTipoComponente INT PRIMARY KEY auto_increment,
    nome VARCHAR(45)
);

INSERT INTO TipoComponente (nome) VALUES 
	('CPU'),
	('Disco'),
	('Memória RAM'),
	('GPU');

CREATE TABLE Componente (
	idComponente INT PRIMARY KEY auto_increment,
    modelo VARCHAR(225),
    fkTelevisao INT,
    fkTipoComponente INT,
    constraint fkTv foreign key (fkTelevisao) references Televisao(idTelevisao),
    constraint fkTipo foreign key (fkTipoComponente) references TipoComponente(idTipoComponente)
);

-- Teste apenas
INSERT INTO Componente (modelo, fkTelevisao, fkTipoComponente) VALUES 
	('I3-6100', 1, 1);
    
SELECT * FROM TipoComponente; 
SELECT * FROM Componente WHERE modelo = 'Sandisk Mve' AND fkTelevisao = 1;
SELECT * FROM Componente JOIN TipoComponente ON fkTipoComponente = idTipoComponente WHERE nome = 'Disco' AND fkTelevisao = 1;
SELECT COUNT(*) FROM Televisao WHERE idTelevisao = 1;

SELECT * FROM Televisao;

SELECT * FROM Componente;
