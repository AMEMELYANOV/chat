CREATE TABLE person_roles (
  person_id INT NOT NULL REFERENCES person(id),
  roles_id INT NOT NULL REFERENCES role(id)
);

COMMENT ON TABLE person_roles IS 'Связи персона - роль';
COMMENT ON COLUMN person_roles.person_id IS 'Идентификатор персоны';
COMMENT ON COLUMN person_roles.roles_id IS 'Идентификатор роли';