CREATE TABLE message (
    id SERIAL PRIMARY KEY,
    content TEXT,
    created TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    person_id INT REFERENCES person(id),
    room_id INT REFERENCES room(id)
);

COMMENT ON TABLE message IS 'Сообщения';
COMMENT ON COLUMN message.id IS 'Идентификатор сообщения';
COMMENT ON COLUMN message.content IS 'Содержание сообщения';
COMMENT ON COLUMN message.created IS 'Дата и время создания сообщения';
COMMENT ON COLUMN message.person_id IS 'Идентификатор персоны';
COMMENT ON COLUMN message.room_id IS 'Идентификатор комнаты';