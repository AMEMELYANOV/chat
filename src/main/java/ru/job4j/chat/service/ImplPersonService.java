package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Реализация сервиса по работе с пользователями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.service.PersonService
 */
@AllArgsConstructor
@Service
public class ImplPersonService implements PersonService {

    /**
     * Объект для доступа к методам PersonRepository
     */
    private final PersonRepository personRepository;

    /**
     * Возвращает список пользователей. Выполняет вызов метода
     * {@link PersonRepository#findAll()} для получения списка
     * пользователей.
     *
     * @return список пользователей
     */
    @Override
    public List<Person> findAll() {
        return StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    /**
     * Возвращает Optional от пользователя по аргументу id.
     * Для получения пользователя вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    /**
     * Сохраняет пользователя в репозитории.
     * Для сохранения пользователя вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param person пользователь
     * @return сохраненный пользователь
     */
    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    /**
     * Удаляет пользователя в репозитории.
     * Для удаления пользователя вызывается метод репозитория
     * {@link PersonRepository#delete(Object)}.
     *
     * @param person пользователь
     */
    @Override
    public void delete(Person person) {
        personRepository.delete(person);
    }

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
    @Override
    public Optional<Person> patchModel(Person person)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(personRepository, person);
    }

    /**
     * Выполняет загрузку и возврат пользователя из репозитория.
     * Для загрузки пользователя вызывается метод репозитория
     * {@link PersonRepository#findByUsername(String)}.
     *
     * @param username имя пользователя
     * @return пользователь
     */
    @Override
    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }
}