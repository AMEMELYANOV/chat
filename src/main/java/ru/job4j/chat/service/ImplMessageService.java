package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.repository.MessageRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Реализация сервиса по работе с сообщениями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.service.MessageService
 */
@AllArgsConstructor
@Service
public class ImplMessageService implements MessageService {

    /**
     * Объект для доступа к методам MessageRepository
     */
    private final MessageRepository messageRepository;

    /**
     * Возвращает список сообщений. Выполняет вызов метода
     * {@link MessageRepository#findAll()} для получения списка
     * сообщений.
     *
     * @return список сообщений
     */
    @Override
    public List<Message> findAll() {
        return this.messageRepository.findAll().stream().collect(Collectors.toList());
    }

    /**
     * Возвращает Optional от сообщения по аргументу id.
     * Для получения сообщения вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор сообщения
     * @return найденное сообщение
     */
    @Override
    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    /**
     * Сохраняет сообщение в репозитории.
     * Для сохранения сообщения вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param message сообщение
     * @return сохраненное сообщение
     */
    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    /**
     * Удаляет сообщение в репозитории.
     * Для удаления сообщения вызывается метод репозитория
     * {@link MessageRepository#delete(Object)}.
     *
     * @param message сообщение
     */
    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

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
    @Override
    public Optional<Message> patchModel(Message message)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(messageRepository, message);
    }
}
