CREATE SCHEMA IF NOT EXISTS duxdb;

SET SCHEMA duxdb;

DROP TABLE IF EXISTS equipo;
CREATE TABLE equipo (
    id BIGINT NOT NULL AUTO_INCREMENT,
    liga VARCHAR(255),
    nombre VARCHAR(255),
    pais VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT UK_l0ojfog3r4a4ppf9kju1y70j7 UNIQUE (nombre)
);

DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    password VARCHAR(255),
    username VARCHAR(255),
    role VARCHAR(255) CHECK (role IN ('USER', 'ADMIN')),
    PRIMARY KEY (id),
    CONSTRAINT UK_863n1y3x0jalatoir4325ehal UNIQUE (username)
);
