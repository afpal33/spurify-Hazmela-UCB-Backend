-- Crear la base de datos
CREATE DATABASE ms_user_hazmelaucb_db;

-- Tabla base de usuarios (datos generales)
CREATE TABLE usuario (
                         id_usuario UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         email VARCHAR(150) UNIQUE NOT NULL,
                         telefono VARCHAR(20),
                         fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         tipo_usuario VARCHAR(20) CHECK (tipo_usuario IN ('ESTUDIANTE', 'ADMIN')),
                         activo BOOLEAN DEFAULT TRUE
);

-- Tabla específica para estudiantes (perfil completo)
CREATE TABLE estudiante (
                            id_usuario UUID PRIMARY KEY REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                            direccion TEXT NOT NULL,
                            fecha_nacimiento DATE NOT NULL,
                            genero VARCHAR(20) CHECK (genero IN ('MASCULINO', 'FEMENINO', 'OTRO')),
                            pais VARCHAR(100) NOT NULL,
                            ciudad VARCHAR(100) NOT NULL,
                            universidad VARCHAR(255) NOT NULL,
                            carrera VARCHAR(255) NOT NULL,
                            semestre INT CHECK (semestre > 0),
                            promedio DECIMAL(3,2) CHECK (promedio BETWEEN 0 AND 10),
                            modalidad_estudio VARCHAR(50) CHECK (modalidad_estudio IN ('PRESENCIAL', 'VIRTUAL', 'HIBRIDO')),
                            experiencia TEXT,
                            proyectos_destacados TEXT,
                            cursos_certificaciones TEXT,
                            intereses_academicos TEXT,
                            linkedin TEXT,
                            github TEXT,
                            website TEXT,
                            foto_perfil TEXT,
                            perfil_completo BOOLEAN DEFAULT FALSE
);

-- Tabla específica para administradores
CREATE TABLE administrador (
                               id_usuario UUID PRIMARY KEY REFERENCES usuario(id_usuario) ON DELETE CASCADE,
                               rol VARCHAR(100) NOT NULL,
                               permisos TEXT[]
);

-- Tabla de habilidades
CREATE TABLE habilidad (
                           id_habilidad SERIAL PRIMARY KEY,
                           nombre VARCHAR(100) UNIQUE NOT NULL
);

-- Relación N a N entre estudiantes y habilidades
CREATE TABLE estudiante_habilidad (
                                      id_usuario UUID REFERENCES estudiante(id_usuario) ON DELETE CASCADE,
                                      id_habilidad INT REFERENCES habilidad(id_habilidad) ON DELETE CASCADE,
                                      PRIMARY KEY (id_usuario, id_habilidad)
);

-- Tabla de auditoría de usuarios
CREATE TABLE auditoria_usuario (
                                   id_auditoria SERIAL PRIMARY KEY,
                                   id_usuario UUID NOT NULL,
                                   accion VARCHAR(50) CHECK (accion IN ('CREAR', 'MODIFICAR', 'ELIMINAR')),
                                   fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   datos_anteriores JSONB,
                                   direccion VARCHAR(100) NOT NULL,
                                   ip VARCHAR(100) NOT NULL,
                                   usuario_admin UUID REFERENCES administrador(id_usuario)
);