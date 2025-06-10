-- ==============================
-- Script de Migración: Cambio de user_id de INTEGER a VARCHAR(36) para soportar UUIDs
-- ==============================

-- IMPORTANTE: Este script debe ejecutarse cuando se tengan datos existentes
-- Si es una instalación nueva, usar directamente dbAnuncios.sql

-- Paso 1: Crear una columna temporal para el nuevo user_id
ALTER TABLE anuncio ADD COLUMN user_id_temp VARCHAR(36);

-- Paso 2: Si hay datos existentes, necesitarás mapear los IDs enteros a UUIDs
-- Ejemplo de migración (ajustar según tus necesidades):
-- UPDATE anuncio SET user_id_temp = 'uuid-correspondiente' WHERE user_id = 1;
-- Esto debe hacerse para cada usuario existente

-- Paso 3: Hacer la nueva columna NOT NULL después de migrar los datos
-- ALTER TABLE anuncio ALTER COLUMN user_id_temp SET NOT NULL;

-- Paso 4: Eliminar la columna antigua
-- ALTER TABLE anuncio DROP COLUMN user_id;

-- Paso 5: Renombrar la columna temporal
-- ALTER TABLE anuncio RENAME COLUMN user_id_temp TO user_id;

-- Paso 6: Crear el índice para el nuevo user_id
-- CREATE INDEX idx_anuncio_user_id ON anuncio (user_id);

-- También actualizar la tabla de auditoría si es necesario
-- ALTER TABLE anuncio_auditoria ADD COLUMN user_id_temp VARCHAR(36);
-- Migrar datos...
-- ALTER TABLE anuncio_auditoria DROP COLUMN user_id;
-- ALTER TABLE anuncio_auditoria RENAME COLUMN user_id_temp TO user_id;
