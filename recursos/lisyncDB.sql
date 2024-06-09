	CREATE DATABASE IF NOT EXISTS lisyncDB;
	USE lisyncDB;
-- DROP DATABASE lisyncDB;

	CREATE TABLE IF NOT EXISTS  Empresa (
		idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
		nomeFantasia VARCHAR(45),
		plano VARCHAR(45),
        cnpj varchar(14),
		CONSTRAINT CHK_Plano CHECK (plano IN('Basico', 'Corporativo', 'Interprise'))
	);
INSERT INTO Empresa (nomeFantasia, plano, cnpj) VALUES ("SP Tech", 'Corporativo',12345678912345 ), ("Elera.", 'Basico',12345678912345);
    

	CREATE TABLE IF NOT EXISTS  ambiente (
		idAmbiente INT PRIMARY KEY AUTO_INCREMENT,
		setor VARCHAR(45),
		andar varchar(45),
		fkEmpresa INT,
		CONSTRAINT fkEmpresaAmbiente FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa)
	);
    
-- INSERT INTO ambiente (setor, andar, fkEmpresa) VALUES ("Marketing", '2',1 );


	CREATE TABLE IF NOT EXISTS  Usuario (
		idUsuario INT PRIMARY KEY AUTO_INCREMENT,
		nome VARCHAR(45),
		email VARCHAR(225),
		senha VARCHAR(45),
		fkEmpresa INT,
		fkGestor INT,
		CONSTRAINT fkEmpresa FOREIGN KEY (fkEmpresa) REFERENCES Empresa(idEmpresa),
		CONSTRAINT fkGestor FOREIGN KEY (fkGestor) REFERENCES Usuario(idUsuario)
	);
	INSERT INTO Usuario (nome, email, senha, fkEmpresa, fkGestor) VALUES 
		("Felipe Almeida", "felipe.almeida@sptech.school", "felipe123", 1, null),
		("Carlos Manoel", "carlos.manoel@sptech.school", "carlos123", 1, 1),
		("Marcela Lopez", "marcela.lopez@elera.io", "marcela123", 2, null),
		("José Felipe", "jose.felipe@elera.io", "jose123", 2, 1),
        ("Ademiro","admin","adminadmin",null,null);
        


	CREATE TABLE IF NOT EXISTS  Televisao (
		idTelevisao INT PRIMARY KEY AUTO_INCREMENT,
		nome VARCHAR(45), 
		taxaAtualizacao INT,
		hostName VARCHAR(80),
		fkAmbiente INT NOT NULL,	
		CONSTRAINT fkAmbiente FOREIGN KEY (fkAmbiente) REFERENCES ambiente(idAmbiente)
	);
    
    
    -- INSERT INTO Televisao values (null, "TV-001", 5000, "akjiduh",1);

	CREATE TABLE IF NOT EXISTS  Componente (
		idComponente INT PRIMARY KEY AUTO_INCREMENT,
		modelo VARCHAR(225),
		identificador VARCHAR(225),
		tipoComponente VARCHAR(45),
		fkTelevisao INT NOT NULL,
		CONSTRAINT fkTv FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);

	CREATE TABLE IF NOT EXISTS  Janela (
		idJanela INT PRIMARY KEY AUTO_INCREMENT,
		pidJanela VARCHAR(45),
		titulo VARCHAR(225),
		localizacao VARCHAR(225),
		visivel VARCHAR(45),
		fkTelevisao INT,
		CONSTRAINT fkTelevisaoJanela FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);

	CREATE TABLE IF NOT EXISTS  Log (
		idLog INT PRIMARY KEY AUTO_INCREMENT,
		pid INT,
		dataHora VARCHAR(45),
		nomeProcesso VARCHAR(80),
		valor DOUBLE,
		fkComponente INT NOT NULL,
		CONSTRAINT fkComponenteLog FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente)
	);

	CREATE TABLE IF NOT EXISTS  LogComponente (
		idLogComponente INT PRIMARY KEY AUTO_INCREMENT,
		dataHora VARCHAR(45),
		valor DOUBLE,
		fkComponente INT NOT NULL,
		CONSTRAINT fkComponenteLogComponente FOREIGN KEY (fkComponente) REFERENCES Componente(idComponente)
	);

	CREATE TABLE IF NOT EXISTS  comando (
		idComando INT PRIMARY KEY AUTO_INCREMENT,
		nome text,
        resposta TEXT,
		fkTelevisao INT,
		CONSTRAINT fkTelevisaoComando FOREIGN KEY (fkTelevisao) REFERENCES Televisao(idTelevisao)
	);
    
   
    
    


    
    
    SELECT *FROM Usuario WHERE fkEmpresa = 1 and fkGestor is null;


	select *from janela;

	select *from log;

	select *from logComponente;

	select *from componente;

	select *from televisao;

	select *from ambiente;

	select *from Usuario;
    
    select *from comando;
    
    select *from empresa;
    
    
    
    
SELECT * FROM empresa ORDER BY idEmpresa DESC LIMIT 1;

SELECT * FROM comando ORDER BY id DESC LIMIT 1;
    
select * from Empresa where nomeFantasia = 'Elera.';
    

    SELECT fkEmpresa as idEmpresa FROM Usuario WHERE email = 'admin' AND senha = 'admin';
    select * from Empresa where nomeFantasia = 'Elera.';
    
    SELECT count(*) FROM Empresa  join ambiente on empresa.idEmpresa = ambiente.fkEmpresa join televisao on ambiente.idAmbiente = televisao.fkAmbiente where fkEmpresa = 1;
    SELECT plano FROM Empresa WHERE idEmpresa = 1;
    
	SELECT comando.idComando , comando.nome, comando.fkTelevisao FROM comando join televisao on comando.fkTelevisao = televisao.idTelevisao where televisao.idTelevisao = 1 ;
    
    select * from Empresa where idEmpresa = 1;
    

     
     
-- INSERT INTO comando  VALUES  (null ,"ipconfig", 1);
     

