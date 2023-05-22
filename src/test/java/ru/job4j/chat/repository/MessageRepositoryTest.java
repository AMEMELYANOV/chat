package ru.job4j.chat.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.chat.model.Message;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища сообщений
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see MessageRepository
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTest {

    /**
     * Объект для доступа к методам MessageRepository
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Сообщение
     */
    private Message message;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        message = Message.builder()
                .content("content")
                .build();
    }

    /**
     * Выполняется проверка нахождения в репозитории списка сообщений,
     * если сообщения сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnMessagesWhenExists() {
        messageRepository.save(message);

        List<Message> messagesFromDB = messageRepository.findAll();

        assertThat(messagesFromDB.size()).isEqualTo(1);
        assertThat(messagesFromDB.get(0).getContent()).isEqualTo(message.getContent());
    }

    /**
     * Выполняется проверка нахождения в репозитории сообщения по id,
     * если сообщение сохранено в репозитории.
     */
    @Test
    public void findByIdShouldReturnMessageWhenExists() {
        int id = messageRepository.save(message).getId();

        Message messageFromDB = messageRepository.findById(id).get();

        assertThat(messageFromDB.getContent()).isEqualTo(message.getContent());
    }

    /**
     * Выполняется проверка нахождения в репозитории сообщения по id,
     * если сообщение не сохранено в репозитории.
     */
    @Test
    public void findByIdShouldReturnOptionalEmptyWhenNotExists() {
        Optional<Message> messageFromDB = messageRepository.findById(0);

        assertThat(messageFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка сохранения сообщения в репозитории.
     */
    @Test
    public void saveShouldReturnMessageWhenSuccess() {
        messageRepository.save(message);

        Optional<Message> messageFromDB = messageRepository.findById(message.getId());

        assertThat(messageFromDB.get().getContent()).isEqualTo(message.getContent());
    }
}