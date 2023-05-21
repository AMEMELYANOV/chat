CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

COMMENT ON TABLE role IS 'Роли';
COMMENT ON COLUMN role.id IS 'Идентификатор роли';
COMMENT ON COLUMN role.name IS 'Наименование роли';