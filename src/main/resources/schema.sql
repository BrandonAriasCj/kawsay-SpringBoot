CREATE DATABASE IF NOT EXISTS kawsay;
USE kawsay;

CREATE TABLE IF NOT EXISTS usuario (
                                       id_usuario VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo_institucional VARCHAR(100) NOT NULL
    );


CREATE TABLE IF NOT EXISTS chat_memory (
                                           id VARCHAR(255) PRIMARY KEY,
    session_id VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    metadata TEXT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
