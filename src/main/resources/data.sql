
CREATE TABLE IF NOT EXISTS public.ai_chat_memory
(
    conversation_id character varying(36) COLLATE pg_catalog."default" NOT NULL,
    content text COLLATE pg_catalog."default" NOT NULL,
    type character varying(10) COLLATE pg_catalog."default" NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    CONSTRAINT ai_chat_memory_type_check CHECK (type::text = ANY (ARRAY['USER'::character varying, 'ASSISTANT'::character varying, 'SYSTEM'::character varying, 'TOOL'::character varying]::text[]))
);



-- Crear tabla de roles
CREATE TABLE rol (
     id_rol SERIAL PRIMARY KEY,
     denominacion VARCHAR(50) NOT NULL,
     estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- Insertar roles
INSERT INTO rol (denominacion) VALUES
   ('alumno'),         -- id_rol = 1
   ('moderador'),      -- id_rol = 2
   ('administrador');  -- id_rol = 3

-- Crear tabla de usuarios
CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    correo_institucional VARCHAR(100) NOT NULL UNIQUE,
    contraseña VARCHAR(100) NOT NULL,
    user_rol INTEGER NOT NULL,
    FOREIGN KEY (user_rol) REFERENCES rol(id_rol)
);

-- Insertar usuarios con rol de alumno (id_rol = 1)
INSERT INTO usuario (correo_institucional, contraseña, user_rol) VALUES
    ('juan.perez@universidad.edu', 'clave123', 1),
    ('maria.lopez@universidad.edu', 'segura456', 1),
    ('carlos.gomez@universidad.edu', 'pass789', 1);

CREATE TABLE historial_preferencias (
    id_historial_preferencias SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    contenido TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);
INSERT INTO historial_preferencias (id_usuario, contenido) VALUES
   (1, 'libros de ciencia ficción y fantasía'),
   (1, 'contenido audiovisual sobre documentales'),
   (2, 'temas de desarrollo personal y liderazgo'),
   (2, 'recomendaciones de libros de negocios'),
   (3, 'podcasts sobre historia mundial'),
   (3, 'música instrumental relajante'),
   (1, 'contenido sobre inteligencia artificial'),
   (2, 'foros sobre filosofía'),
   (3, 'cursos de programación');
