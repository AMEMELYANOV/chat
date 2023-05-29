package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с комнатами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Room
 */
public interface RoomService {

    /**
     * Возвращает список комнат. Выполняет вызов метода
     * {@link RoomRepository#findAll()} для получения списка
     * комнат.
     *
     * @return список комнат
     */
    List<Room> findAll();

    /**
     * Возвращает Optional от комнаты по аргументу id.
     * Для получения комнаты вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор комнаты
     * @return найденная комната
     */
    Optional<Room> findById(int id);

    /**
     * Сохраняет комнату в репозитории.
     * Для сохранения комнаты вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param room комната
     * @return сохраненная комната
     */
    Room save(Room room);

    /**
     * Удаляет комнату в репозитории.
     * Для удаления комнаты вызывается метод репозитория
     * {@link RoomRepository#delete(Object)}.
     *
     * @param room комната
     */
    void delete(Room room);

    /**
     * Обновляет комнату в репозитории.
     * Для обновления комнаты вызывается метод сервиса
     * {@link DTOService#patchModel(CrudRepository, Model)}.
     *
     * @param room комната
     * @return обновленная комната
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    Optional<Room> patchModel(Room room)
            throws InvocationTargetException, IllegalAccessException;
}
