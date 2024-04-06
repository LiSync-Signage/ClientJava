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

SELECT * FROM Empresa;

INSERT INTO Usuario (nome, email, senha, fkEmpresa) VALUES 
	("Matheus Shoji", "matheus.shoji@sptech.school", "matheus_shoji123", 1),
	("Gabriel Shoji", "gabriel.shoji@sptech.school", "gabriel123", 2),
	("Raquel Shoji", "raquel.shoji@sptech.school", "raquel_shoji123", null);

SELECT * FROM Usuario;
SELECT * FROM Usuario WHERE email = "matheus.shoji@sptech.school" AND senha = "matheus_shoji123";
