-- Este script fue generado por el ERD tool en pgAdmin 4
-- Corrige el error de clave foránea en rating_auditoria(user_id)
BEGIN;

CREATE TABLE IF NOT EXISTS public.rating
(
    id_rating serial PRIMARY KEY,
    rating integer,
    rated_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS public.anuncio
(
    id_anuncio serial PRIMARY KEY,
    user_id integer UNIQUE,  -- Se agregó UNIQUE para que pueda ser referenciado
    titulo character varying(255),
    descripcion text,
    area_especializacion character varying(100),
    precio numeric(10, 2) NOT NULL,
    estado character varying(50),
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);

CREATE TABLE IF NOT EXISTS public.rating_anuncio
(
    id_ra serial PRIMARY KEY,
    anuncio_id integer,
    rating_id integer
);

CREATE TABLE IF NOT EXISTS public.rating_auditoria
(
    id_rau serial PRIMARY KEY,
    rating_id integer,
    anuncio_id integer,
    user_id integer,
    cambio character varying(500),
    fecha_cambio timestamp with time zone NOT NULL
);

-- Claves foráneas

ALTER TABLE IF EXISTS public.rating_anuncio
    ADD FOREIGN KEY (rating_id)
    REFERENCES public.rating (id_rating) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.rating_anuncio
    ADD FOREIGN KEY (anuncio_id)
    REFERENCES public.anuncio (id_anuncio) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.rating_auditoria
    ADD FOREIGN KEY (rating_id)
    REFERENCES public.rating (id_rating) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.rating_auditoria
    ADD FOREIGN KEY (user_id)
    REFERENCES public.anuncio (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

ALTER TABLE IF EXISTS public.rating_auditoria
    ADD FOREIGN KEY (anuncio_id)
    REFERENCES public.anuncio (id_anuncio) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

COMMIT;
