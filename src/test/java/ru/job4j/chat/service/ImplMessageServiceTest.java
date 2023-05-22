package ru.job4j.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.chat.ChatApplication;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.repository.MessageRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

/**
 * Тест класс реализации сервисного слоя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.service.ImplMessageService
 */
@SpringBootTest(classes = ChatApplication.class)
class ImplMessageServiceTest {

    /**
     * Объект заглушка для MessageRepository
     */
    @MockBean
    private MessageRepository messageRepository;

    /**
     * Объект для доступа к методам MessageService
     */
    private MessageService messageService;

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
                .content("text")
                .build();
        messageService = new ImplMessageService(messageRepository);
    }

    /**
     * Выполняется проверка нахождения в messageRepository списка сообщений,
     * если сообщения сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnMessagesWhenExists() {
        doReturn(List.of(message)).when(messageRepository).findAll();

        List<Message> messages = messageService.findAll();

        assertThat(messages.size()).isEqualTo(1);
        assertThat(messages.get(0).getContent()).isEqualTo(message.getContent());
    }

    /**
     * Выполняется проверка возврата от messageRepository пустого списка сообщений,
     * если сообщения не сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnEmptyListWhenNotExists() {
        doReturn(List.of()).when(messageRepository).findAll();

        List<Message> messages = messageService.findAll();

        assertThat(messages.size()).isEqualTo(0);
    }


    /**
     * Выполняется проверка возвращения сообщения при возврате
     * от messageRepository, если сообщение найдено по id.
     */
    @Test
    public void findByIdShouldReturnMessageWhenExists() {
        doReturn(Optional.of(message)).when(messageRepository).findById(anyInt());

        Optional<Message> messageFromDB = messageService.findById(anyInt());

        assertThat(messageFromDB.get().getContent()).isEqualTo(message.getContent());
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от messageRepository, если сообщение не найдено по id.
     */
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenNotExists() {
        doReturn(Optional.empty()).when(messageRepository).findById(anyInt());

        Optional<Message> messageFromDB = messageService.findById(anyInt());

        assertThat(messageFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка возвращения сообщения при возврате
     * от messageRepository, если сообщение сохранено.
     */
    @Test
    public void saveShouldReturnMessageWhenSuccess() {
        doReturn(message).when(messageRepository).save(message);

        Message messageFromDB = messageService.save(message);

        assertThat(messageFromDB).isEqualTo(message);
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от messageRepository, если сообщение не существует.
     */
    @Test
    public void patchModelShouldReturnOptionalEmptyWhenNotExists()
            throws InvocationTargetException, IllegalAccessException {
        doReturn(Optional.empty()).when(messageRepository).findById(anyInt());

        Optional<Message> messageFromDB = messageService.patchModel(message);

        assertThat(messageFromDB).isEqualTo(Optional.empty());
    }

}