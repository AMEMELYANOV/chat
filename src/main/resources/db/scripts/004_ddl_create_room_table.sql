CREATE TABLE room (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

COMMENT ON TABLE room IS 'Комнаты';
COMMENT ON COLUMN room.id IS 'Идентификатор комнаты';
COMMENT ON COLUMN room.name IS 'Наименование комнаты';