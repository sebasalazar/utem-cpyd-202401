BEGIN TRANSACTION;

DROP TABLE IF EXISTS credentials CASCADE;
CREATE TABLE credentials (
    pk bigserial NOT NULL, -- bigint AUTOINCREMENT (secuencia)
    code varchar(255) NOT NULL, -- Código UNICO de la credencial
    ws_tkn varchar(255) NOT NULL, -- nombre de usuario (token de webservice)
    ws_key varchar(255) NOT NULL, -- contraseña (llave del webservice)
    created timestamptz NOT NULL DEFAULT NOW(), -- Fecha de creación del registro
    updated timestamptz NOT NULL DEFAULT NOW(), -- Fecha de última actualización del registro
    UNIQUE (ws_tkn, ws_key),
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX cred_code_uidx ON credentials(UPPER(code));


DROP TABLE IF EXISTS entrances CASCADE;
CREATE TABLE entrances (
    pk bigserial NOT NULL, -- bigint AUTOINCREMENT (secuencia)
    credential_fk bigint NOT NULL,
    user_agent text,
    ip varchar(255),
    created timestamptz NOT NULL DEFAULT NOW(), -- Fecha de creación del registro
    updated timestamptz NOT NULL DEFAULT NOW(), -- Fecha de última actualización del registro
    FOREIGN KEY (credential_fk) REFERENCES credentials(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);


DROP TABLE IF EXISTS faculties CASCADE;
CREATE TABLE faculties (
    pk bigserial NOT NULL, -- bigint AUTOINCREMENT (secuencia)
    code varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    address varchar(255) NOT NULL,
    latitude real NOT NULL,
    longitude real NOT NULL,
    created timestamptz NOT NULL DEFAULT NOW(), -- Fecha de creación del registro
    updated timestamptz NOT NULL DEFAULT NOW(), -- Fecha de última actualización del registro
    FOREIGN KEY (credential_fk) REFERENCES credentials(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX fac_code_uidx ON faculties(UPPER(code));

COMMIT;
