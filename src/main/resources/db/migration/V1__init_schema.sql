-- ============================================================
-- V1__init_schema.sql
-- Laboratorio de Archivística UD — esquema inicial
-- ============================================================

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id                   BIGSERIAL PRIMARY KEY,
    email                VARCHAR(255) NOT NULL UNIQUE,
    name                 VARCHAR(255) NOT NULL,
    password             VARCHAR(60)  NOT NULL,
    role                 VARCHAR(20)  NOT NULL CHECK (role IN ('ADMIN', 'DOCENTE', 'ESTUDIANTE')),
    active               BOOLEAN      NOT NULL DEFAULT TRUE,
    last_login           TIMESTAMP,
    password_changed_at  TIMESTAMP,
    created_at           TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMP    NOT NULL DEFAULT NOW(),
    version              BIGINT       NOT NULL DEFAULT 0
);

-- Tabla de aplicativos
CREATE TABLE IF NOT EXISTS aplicativos (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    app_version  VARCHAR(50)  NOT NULL,
    description  TEXT,
    access_url   VARCHAR(500),
    category     VARCHAR(100),
    icon_url     VARCHAR(500),
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    version      BIGINT       NOT NULL DEFAULT 0
);

-- ============================================================
-- Superusuario ADMIN por defecto
-- Credenciales: admin@udistrital.edu.co / admin123
-- Hash BCrypt de 'admin123' (strength=10)
-- ============================================================
INSERT INTO usuarios (email, name, password, role, active, created_at, updated_at)
VALUES (
    'admin@udistrital.edu.co',
    'Administrador Lab Archivística',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    'ADMIN',
    TRUE,
    NOW(),
    NOW()
)
ON CONFLICT (email) DO NOTHING;

-- ============================================================
-- Aplicativos de ejemplo (seeds)
-- ============================================================
INSERT INTO aplicativos (name, app_version, description, access_url, category, icon_url, created_at, updated_at)
VALUES
    ('DSpace',
     '7.6',
     'Repositorio institucional de objetos digitales. Permite gestionar, preservar y dar acceso a colecciones digitales.',
     '/apps/dspace',
     'Repositorio',
     '/icons/dspace.svg',
     NOW(), NOW()),
    ('Archivematica',
     '1.15',
     'Sistema de preservación digital de largo plazo basado en estándares OAIS.',
     '/apps/archivematica',
     'Preservación',
     '/icons/archivematica.svg',
     NOW(), NOW()),
    ('AtoM',
     '2.7',
     'Access to Memory — sistema de descripción archivística basado en normas ICA (ISAD-G, ISAAR).',
     '/apps/atom',
     'Descripción',
     '/icons/atom.svg',
     NOW(), NOW()),
    ('Orfeo',
     '4.0',
     'Sistema de gestión documental para la administración de correspondencia y flujos documentales.',
     '/apps/orfeo',
     'Gestión Documental',
     '/icons/orfeo.svg',
     NOW(), NOW())
ON CONFLICT DO NOTHING;
