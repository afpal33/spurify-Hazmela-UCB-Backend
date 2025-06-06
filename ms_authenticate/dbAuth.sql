

-- Tabla de Usuarios de Autenticación
-- Tabla de Usuarios de Autenticación
CREATE TABLE auth_users (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            email VARCHAR(100) UNIQUE NOT NULL,
                            hashed_password TEXT,  -- NULL si usa Google OAuth
                            auth_method VARCHAR(20) CHECK (auth_method IN ('EMAIL', 'GOOGLE')) NOT NULL,
                            is_active BOOLEAN DEFAULT TRUE,
                            is_locked BOOLEAN DEFAULT FALSE,
                            failed_attempts INT DEFAULT 0,
                            last_login TIMESTAMP DEFAULT NULL,
                            last_login_ip TEXT,  -- NUEVO: dirección IP del último login
                            last_login_user_agent TEXT,  -- NUEVO: user agent del último login
                            google_id TEXT,  -- opcional para OAuth con Google
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Roles (Para permisos en el sistema de autenticación)
CREATE TABLE roles (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       name VARCHAR(50) UNIQUE NOT NULL
);

-- Tabla de Permisos (ACL basada en roles)
CREATE TABLE permissions (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             name VARCHAR(100) UNIQUE NOT NULL
);

-- Relación Usuarios-Roles
CREATE TABLE user_roles (
                            user_id UUID REFERENCES auth_users(id) ON DELETE CASCADE,
                            role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);

-- Relación Roles-Permisos
CREATE TABLE role_permissions (
                                  role_id UUID REFERENCES roles(id) ON DELETE CASCADE,
                                  permission_id UUID REFERENCES permissions(id) ON DELETE CASCADE,
                                  PRIMARY KEY (role_id, permission_id)
);

-- Intentos de Inicio de Sesión (Para auditoría y detección de ataques)
CREATE TABLE login_attempts (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                user_id UUID REFERENCES auth_users(id) ON DELETE CASCADE,
                                success BOOLEAN NOT NULL,
                                ip VARCHAR(50) NOT NULL,
                                user_agent TEXT NOT NULL,
                                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Sesiones Activas (Para controlar sesiones activas y dispositivos)
CREATE TABLE active_sessions (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 user_id UUID REFERENCES auth_users(id) ON DELETE CASCADE,
                                 refresh_token TEXT NOT NULL UNIQUE,
                                 ip VARCHAR(50) NOT NULL,
                                 user_agent TEXT NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 expiry_date TIMESTAMP NOT NULL
);

-- Lista Negra de Tokens (Para evitar reutilización de tokens revocados)
CREATE TABLE revoked_tokens (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                jwt_token TEXT NOT NULL UNIQUE,
                                revoked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de Auditoría (Para registrar accesos importantes)
CREATE TABLE audit_logs (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            user_id UUID REFERENCES auth_users(id) ON DELETE CASCADE,
                            action VARCHAR(50) CHECK (action IN ('LOGIN', 'LOGOUT', 'PASSWORD_CHANGE', 'TOKEN_REVOKE')) NOT NULL,
    ip VARCHAR(50) NOT NULL,
    user_agent TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Inserción de roles por defecto
INSERT INTO roles(name) VALUES ('ESTUDIANTE');
INSERT INTO roles(name) VALUES ('ADMIN');