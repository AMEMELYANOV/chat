package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Person;

/**
 * Хранилище пользователей
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Person
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {

    /**
     * Возвращает пользователя по имени.
     *
     * @param username имя пользователя
     * @return пользователь
     */
    Person findByUsername(String username);
}
