-- ==============================
-- Creación de la Base de Datos
-- ==============================
CREATE DATABASE anuncios_db;
\c anuncios_db; -- Conectar a la base de datos creada

-- ==============================
-- Tabla: anuncio
-- ==============================
CREATE TABLE anuncio (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL, -- ID del usuario (de otro microservicio)
    titulo VARCHAR(255) NOT NULL,
    descripcion TEXT NOT NULL,
    area_especializacion VARCHAR(100) NOT NULL,
    precio NUMERIC(10,2) CHECK (precio >= 0), -- Opcional y con validación
    estado VARCHAR(50) NOT NULL DEFAULT 'DRAFT', -- 'DRAFT', 'PUBLISHED', 'ARCHIVED'
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- Índices para optimizar búsquedas
CREATE INDEX idx_anuncio_area ON anuncio (area_especializacion);
CREATE INDEX idx_anuncio_precio ON anuncio (precio);
CREATE INDEX idx_anuncio_estado ON anuncio (estado);

-- ==============================
-- Tabla: tag (etiquetas de especialización)
-- ==============================
CREATE TABLE tag (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- ==============================
-- Tabla intermedia: anuncio_tag (relación muchos a muchos)
-- ==============================
CREATE TABLE anuncio_tag (
    anuncio_id INTEGER NOT NULL,
    tag_id INTEGER NOT NULL,
    PRIMARY KEY (anuncio_id, tag_id),
    FOREIGN KEY (anuncio_id) REFERENCES anuncio (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);

-- ==============================
-- Tabla: anuncio_auditoria (Historial de cambios)
-- ==============================
CREATE TABLE anuncio_auditoria (
    id SERIAL PRIMARY KEY,
    anuncio_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL, -- Usuario que realizó el cambio
    cambio VARCHAR(500) NOT NULL, -- Breve descripción del cambio
    fecha_cambio TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (anuncio_id) REFERENCES anuncio (id) ON DELETE CASCADE
);

-- ==============================
-- Trigger para actualizar "updated_at" en la tabla "anuncio"
-- ==============================
CREATE OR REPLACE FUNCTION actualizar_fecha_modificacion()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_actualizar_fecha
BEFORE UPDATE ON anuncio
FOR EACH ROW
EXECUTE FUNCTION actualizar_fecha_modificacion();
