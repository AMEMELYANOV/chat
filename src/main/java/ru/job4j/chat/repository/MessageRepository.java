package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище сообщений
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Message
 */
public interface MessageRepository extends CrudRepository<Message, Integer> {

    /**
     * Возвращает список сообщений.
     *
     * @return список сообщений
     */
    @EntityGraph(attributePaths = {"person", "room"})
    List<Message> findAll();

    /**
     * Возвращает сообщение по идентификатору.
     *
     * @param id идентификатор сообщения
     * @return сообщение
     */
    @EntityGraph(attributePaths = {"person", "room"})
    Optional<Message> findById(int id);
}
