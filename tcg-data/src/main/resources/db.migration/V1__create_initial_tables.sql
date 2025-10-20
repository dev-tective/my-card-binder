-- V1__create_initial_schema.sql

----------------------------------------------------
-- 1. TABLA card_sets (Conjuntos/Expansiones)
----------------------------------------------------
CREATE TABLE card_sets (
                           id BIGSERIAL PRIMARY KEY,

    -- Claves de negocio
                           tcg VARCHAR(50) NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           code VARCHAR(255) NOT NULL,

    -- Contenido
                           description TEXT,
                           images TEXT[], -- Mapea a List<String> con @JdbcTypeCode(SqlTypes.ARRAY) en PostgreSQL
                           release_date DATE,

    -- Restricciones de Unicidad
                           CONSTRAINT uc_cardset_name UNIQUE (name),
                           CONSTRAINT uc_cardset_code UNIQUE (code)
);

-- Índices en card_sets para búsquedas rápidas
CREATE INDEX idx_cardset_tcg ON card_sets (tcg);


----------------------------------------------------
-- 2. TABLA cards (Cartas Individuales)
----------------------------------------------------
CREATE TABLE cards (
                       id BIGSERIAL PRIMARY KEY,

    -- Propiedades Clave
                       name VARCHAR(255) NOT NULL,
                       code VARCHAR(255) NOT NULL,
                       rarity VARCHAR(50) NOT NULL,
                       rarity_code VARCHAR(50),

    -- Propiedad inmutable (updatable = false)
                       tcg VARCHAR(50) NOT NULL,

                       type VARCHAR(100) NOT NULL,
                       subtypes TEXT[], -- Mapea a List<String>
                       archetype VARCHAR(255),

    -- Propiedades de contenido
                       description TEXT,
                       details_url TEXT,
                       image TEXT,

    -- Contador de popularidad
                       wanted BIGINT DEFAULT 0,

    -- Clave Foránea a CardSet
                       card_set_id BIGINT,

    -- Definición de clave foránea
                       CONSTRAINT fk_card_set
                           FOREIGN KEY (card_set_id)
                               REFERENCES card_sets (id)
);

-- Índices en cards para optimización de búsqueda
CREATE INDEX idx_card_name ON cards (name);
CREATE INDEX idx_card_wanted ON cards (wanted);
CREATE INDEX idx_card_set_code ON cards (card_set_id, code);