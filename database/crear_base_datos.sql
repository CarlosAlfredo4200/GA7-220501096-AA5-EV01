/*
 * Script de apoyo para la evidencia GA7-220501096-AA5-EV01.
 * Ejecutar con una cuenta con permisos para crear bases de datos.
 *
 * La tabla usuario es creada/actualizada automáticamente por Hibernate
 * mediante spring.jpa.hibernate.ddl-auto=update.
 */

IF DB_ID('auth_sena') IS NULL
BEGIN
    CREATE DATABASE auth_sena;
END;
GO

USE auth_sena;
GO

/*
 * Si el login usuario_papeleria ya existe en el servidor,
 * ejecutar este bloque para habilitarlo en la base de datos.
 */
IF NOT EXISTS (
    SELECT 1
    FROM sys.database_principals
    WHERE name = 'usuario_papeleria'
)
BEGIN
    CREATE USER usuario_papeleria FOR LOGIN usuario_papeleria;
END;
GO

ALTER ROLE db_datareader ADD MEMBER usuario_papeleria;
ALTER ROLE db_datawriter ADD MEMBER usuario_papeleria;
ALTER ROLE db_ddladmin ADD MEMBER usuario_papeleria;
GO
