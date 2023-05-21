CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

COMMENT ON TABLE person IS 'Персоны';
COMMENT ON COLUMN person.id IS 'Идентификатор персоны';
COMMENT ON COLUMN person.username IS 'Имя персоны';
COMMENT ON COLUMN person.password IS 'Пароль персоны';