-- ==========================================
-- 1) Tabla "conversacion chat"
-- ==========================================
CREATE TABLE conversacion (
                              conversacion_id SERIAL PRIMARY KEY,
                              user1_id        BIGINT NOT NULL,
                              user2_id        BIGINT NOT NULL,
                              creado          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              se_envio        BIGINT
);

-- ==========================================
-- 2) Tabla "chat_mensaje"
-- ==========================================
CREATE TABLE chat_mensaje (
                              mensaje_id      SERIAL PRIMARY KEY,
                              sender_id       BIGINT NOT NULL,
                              contenido       TEXT   NOT NULL,
                              creacion        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              leido           TIMESTAMP,
                              conversacion_id BIGINT NOT NULL,
                              eliminado       TIMESTAMP,
                              CONSTRAINT fk_conversacion
                                  FOREIGN KEY (conversacion_id)
                                      REFERENCES conversacion (conversacion_id)
);
