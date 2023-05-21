package ru.job4j.chat.service;

import ru.job4j.chat.model.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<Message> findAll();

    Optional<Message> findById(int id);

    Message save(Message message);

    void delete(Message message);

    Optional<Message> patchModel(Message message)
            throws InvocationTargetException, IllegalAccessException;
}
