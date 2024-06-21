CREATE DATABASE IF NOT EXISTS bdcoldigo;

USE bdcoldigo;

CREATE TABLE IF NOT EXISTS marcas (
    id INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    status TINYINT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS produtos (
	id INT NOT NULL AUTO_INCREMENT,
	categoria TINYINT NOT NULL,
	modelo VARCHAR(45) NOT NULL,
	capacidade INT NOT NULL,
	valor DECIMAL(7,2) NOT NULL,
	marcas_id INT NOT NULL,
	PRIMARY KEY (id),
	INDEX fk_produtos_marcas_idx (marcas_id ASC),
		CONSTRAINT fk_produtos_marcas
		FOREIGN KEY (marcas_id) 
		REFERENCES marcas (id)
);