package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с пользователями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Person
 */
public interface PersonService {

    /**
     * Возвращает список пользователей. Выполняет вызов метода
     * {@link PersonRepository#findAll()} для получения списка
     * пользователей.
     *
     * @return список пользователей
     */
    List<Person> findAll();

    /**
     * Возвращает Optional от пользователя по аргументу id.
     * Для получения пользователя вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    Optional<Person> findById(int id);

    /**
     * Сохраняет пользователя в репозитории.
     * Для сохранения пользователя вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param person пользователь
     * @return сохраненный пользователь
     */
    Person save(Person person);

    /**
     * Удаляет пользователя в репозитории.
     * Для удаления пользователя вызывается метод репозитория
     * {@link PersonRepository#delete(Object)}.
     *
     * @param person пользователь
     */
    void delete(Person person);

    /**
     * Обновляет пользователя в репозитории.
     * Для обновления пользователя вызывается метод сервиса
     * {@link DTOService#patchModel(CrudRepository, Model)}.
     *
     * @param person пользователь
     * @return обновленный пользователь
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    Optional<Person> patchModel(Person person)
            throws InvocationTargetException, IllegalAccessException;

    /**
     * Выполняет загрузку и возврат пользователя из репозитория.
     * Для загрузки пользователя вызывается метод репозитория
     * {@link PersonRepository#findByUsername(String)}.
     *
     * @param username имя пользователя
     * @return пользователь
     */
    Person findByUsername(String username);
}