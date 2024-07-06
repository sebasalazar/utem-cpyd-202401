BEGIN TRANSACTION;

INSERT INTO credentials (code, ws_tkn, ws_key) VALUES ('92e99dfd-1b08-44c1-b842-ddd80fd44cdd','abc123','xyz987');


INSERT INTO faculties (code, name, address, latitude, longitude) VALUES ('FAE','Facultad de Administración y Economía','Dr. Hernán Alessandri 644, 7500998 Santiago, Providencia, Región Metropolitana','-33.43459','-70.6261097');
INSERT INTO faculties (code, name, address, latitude, longitude) VALUES ('FCCOT','Facultad de Ciencias de la Construcción y Ordenamiento Territorial','Dieciocho 390, 8330526 Santiago, Región Metropolitana','-33.4512277','-70.6577418');
INSERT INTO faculties (code, name, address, latitude, longitude) VALUES ('FCNMM','Facultad de Ciencias Naturales, Matemática y del Medio Ambiente','Las Palmeras, 7780125 Ñuñoa, Región Metropolitana','-33.4667771','-70.6006914');
INSERT INTO faculties (code, name, address, latitude, longitude) VALUES ('FHTCS','Facultad de Humanidades y Tecnologías de la Comunicación Social','Dieciocho 390, 8330526 Santiago, Región Metropolitana','-33.4512206','-70.6597914');
INSERT INTO faculties (code, name, address, latitude, longitude) VALUES ('FING','Facultad de Ingeniería','Av. José Pedro Alessandri 1242, Ñuñoa, Región Metropolitana','-33.4661747','-70.6014343');

COMMIT;
