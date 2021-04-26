-- Generado por Oracle SQL Developer Data Modeler 19.2.0.182.1216
--   en:        2019-12-05 20:56:14 CET
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



CREATE TABLE asignaturas (
    codigo   VARCHAR2(255) NOT NULL,
    nombre   VARCHAR2(255) NOT NULL,
    centro   VARCHAR2(255) NOT NULL
);

ALTER TABLE asignaturas ADD CONSTRAINT asignaturas_pk PRIMARY KEY ( codigo );

CREATE TABLE empleados (
    dni          VARCHAR2(10) NOT NULL,
    apellidos    VARCHAR2(255) NOT NULL,
    nombre       VARCHAR2(255) NOT NULL,
    ong_codigo   NUMBER(255) NOT NULL,
    cargo        VARCHAR2(255) NOT NULL
);

ALTER TABLE empleados ADD CONSTRAINT empleados_pk PRIMARY KEY ( dni,
                                                                ong_codigo );

CREATE TABLE mensaje (
    fecha                        DATE NOT NULL,
    titulo                       VARCHAR2(255) NOT NULL,
    contenido                    VARCHAR2(1000) NOT NULL,
    empleados_dni                VARCHAR2(10) NOT NULL,
    empleados_codigo             NUMBER(255) NOT NULL,
    ong_codigo                   NUMBER(255) NOT NULL,
    usuario_correo_electronico   VARCHAR2(255) NOT NULL
);

ALTER TABLE mensaje
    ADD CONSTRAINT mensaje_pk PRIMARY KEY ( fecha,
                                            ong_codigo,
                                            usuario_correo_electronico );

CREATE TABLE ong (
    codigo        NUMBER(255) NOT NULL,
    nombre        VARCHAR2(255) NOT NULL,
    descripcion   VARCHAR2(255) NOT NULL,
    web           VARCHAR2(100)
);

ALTER TABLE ong ADD CONSTRAINT ong_pk PRIMARY KEY ( codigo );

CREATE TABLE relation_13 (
    usuario_correo_electronico   VARCHAR2(255) NOT NULL,
    asignaturas_codigo           VARCHAR2(255) NOT NULL
);

ALTER TABLE relation_13 ADD CONSTRAINT relation_13_pk PRIMARY KEY ( usuario_correo_electronico,
                                                                    asignaturas_codigo );

CREATE TABLE relation_15 (
    usuario_correo_electronico   VARCHAR2(255) NOT NULL,
    servicios_codigo             NUMBER(255) NOT NULL,
    servicios_codigo1            NUMBER(255) NOT NULL
);

ALTER TABLE relation_15
    ADD CONSTRAINT relation_15_pk PRIMARY KEY ( usuario_correo_electronico,
                                                servicios_codigo,
                                                servicios_codigo1 );

CREATE TABLE relation_7 (
    servicios_codigo       NUMBER(255) NOT NULL,
    servicios_ong_codigo   NUMBER(255) NOT NULL,
    empleados_dni          VARCHAR2(10) NOT NULL,
    empleados_ong_codigo   NUMBER(255) NOT NULL
);

ALTER TABLE relation_7
    ADD CONSTRAINT relation_7_pk PRIMARY KEY ( servicios_codigo,
                                               servicios_ong_codigo,
                                               empleados_dni,
                                               empleados_ong_codigo );

CREATE TABLE servicios (
    codigo          NUMBER(255) NOT NULL,
    nombre          VARCHAR2(255) NOT NULL,
    tipo            NUMBER(1),
    descripcion     VARCHAR2(1000) NOT NULL,
    horario_rango   VARCHAR2(255) NOT NULL,
    fecha_rango     VARCHAR2(255) NOT NULL,
    ubicacion       VARCHAR2(255) NOT NULL,
    ong_codigo      NUMBER(255) NOT NULL
);

ALTER TABLE servicios ADD CONSTRAINT servicios_pk PRIMARY KEY ( codigo,
                                                                ong_codigo );

CREATE TABLE usuario (
    correo_electronico           VARCHAR2(255) NOT NULL,
    dni                          VARCHAR2(10) NOT NULL,
    nombre                       VARCHAR2(255) NOT NULL,
    apellidos                    VARCHAR2(255) NOT NULL,
    contraseña                   VARCHAR2(255) NOT NULL,
    telefono                     VARCHAR2(255),
    ocupacion                    VARCHAR2(255) NOT NULL,
    dni1                         VARCHAR2 
--  ERROR: VARCHAR2 size not specified 
    ,
    disponibilidad               VARCHAR2(255),
    turno                        VARCHAR2(255),
    usuario_correo_electronico   VARCHAR2(255)
);

ALTER TABLE usuario ADD CONSTRAINT usuario_pk PRIMARY KEY ( correo_electronico );

ALTER TABLE usuario ADD CONSTRAINT usuario_dni_un UNIQUE ( dni );

ALTER TABLE empleados
    ADD CONSTRAINT empleados_ong_fk FOREIGN KEY ( ong_codigo )
        REFERENCES ong ( codigo );

ALTER TABLE mensaje
    ADD CONSTRAINT mensaje_empleados_fk FOREIGN KEY ( empleados_dni,
                                                      empleados_codigo )
        REFERENCES empleados ( dni,
                               ong_codigo );

ALTER TABLE mensaje
    ADD CONSTRAINT mensaje_ong_fk FOREIGN KEY ( ong_codigo )
        REFERENCES ong ( codigo );

ALTER TABLE mensaje
    ADD CONSTRAINT mensaje_usuario_fk FOREIGN KEY ( usuario_correo_electronico )
        REFERENCES usuario ( correo_electronico );

ALTER TABLE relation_13
    ADD CONSTRAINT relation_13_asignaturas_fk FOREIGN KEY ( asignaturas_codigo )
        REFERENCES asignaturas ( codigo );

ALTER TABLE relation_13
    ADD CONSTRAINT relation_13_usuario_fk FOREIGN KEY ( usuario_correo_electronico )
        REFERENCES usuario ( correo_electronico );

ALTER TABLE relation_15
    ADD CONSTRAINT relation_15_servicios_fk FOREIGN KEY ( servicios_codigo,
                                                          servicios_codigo1 )
        REFERENCES servicios ( codigo,
                               ong_codigo );

ALTER TABLE relation_15
    ADD CONSTRAINT relation_15_usuario_fk FOREIGN KEY ( usuario_correo_electronico )
        REFERENCES usuario ( correo_electronico );

ALTER TABLE relation_7
    ADD CONSTRAINT relation_7_empleados_fk FOREIGN KEY ( empleados_dni,
                                                         empleados_ong_codigo )
        REFERENCES empleados ( dni,
                               ong_codigo );

ALTER TABLE relation_7
    ADD CONSTRAINT relation_7_servicios_fk FOREIGN KEY ( servicios_codigo,
                                                         servicios_ong_codigo )
        REFERENCES servicios ( codigo,
                               ong_codigo );

ALTER TABLE servicios
    ADD CONSTRAINT servicios_ong_fk FOREIGN KEY ( ong_codigo )
        REFERENCES ong ( codigo );

ALTER TABLE usuario
    ADD CONSTRAINT usuario_usuario_fk FOREIGN KEY ( usuario_correo_electronico )
        REFERENCES usuario ( correo_electronico );



-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             9
-- CREATE INDEX                             0
-- ALTER TABLE                             22
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   1
-- WARNINGS                                 0
