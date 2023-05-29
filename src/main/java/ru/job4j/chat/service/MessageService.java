package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с сообщениями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Message
 */
public interface MessageService {

    /**
     * Возвращает список сообщений. Выполняет вызов метода
     * {@link MessageRepository#findAll()} для получения списка
     * сообщений.
     *
     * @return список сообщений
     */
    List<Message> findAll();

    /**
     * Возвращает Optional от сообщения по аргументу id.
     * Для получения сообщения вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор сообщения
     * @return найденное сообщение
     */
    Optional<Message> findById(int id);

    /**
     * Сохраняет сообщение в репозитории.
     * Для сохранения сообщения вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param message сообщение
     * @return сохраненное сообщение
     */
    Message save(Message message);

    /**
     * Удаляет сообщение в репозитории.
     * Для удаления сообщения вызывается метод репозитория
     * {@link MessageRepository#delete(Object)}.
     *
     * @param message сообщение
     */
    void delete(Message message);

    /**
     * Обновляет сообщение в репозитории.
     * Для обновления сообщения вызывается метод сервиса
     * {@link DTOService#patchModel(CrudRepository, Model)}.
     *
     * @param message сообщение
     * @return обновленное сообщение
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    Optional<Message> patchModel(Message message)
            throws InvocationTargetException, IllegalAccessException;
}
