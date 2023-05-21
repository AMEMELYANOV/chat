package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ImplMessageService implements MessageService {

    private final MessageRepository messageRepository;

    @Override
    public List<Message> findAll() {
        return StreamSupport.stream(
                this.messageRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findById(int id) {
        return messageRepository.findById(id);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void delete(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public Optional<Message> patchModel(Message message)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(messageRepository, message);
    }
}
