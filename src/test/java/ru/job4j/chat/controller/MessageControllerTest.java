package ru.job4j.chat.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.chat.ChatApplication;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.ImplMessageService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тест класс реализации контроллеров
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.controller.MessageController
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class MessageControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Объект заглушка для ImplMessageService
     */
    @MockBean
    private ImplMessageService messageService;

    /**
     * Выполняется проверка выполнения GET запроса для возвращения
     * списка всех сообщений и проверка вызова метода сервисного
     * слоя {@link ImplMessageService#findAll()}.
     */
    @Test
    public void shouldReturnAllMessages() throws Exception {
        this.mockMvc.perform(get("/message/"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(messageService).findAll();
    }

    /**
     * Выполняется проверка выполнения GET запроса для поиска сообщения по id,
     * если сообщение не найдено и проверка вызова метода сервисного слоя
     * {@link ImplMessageService#findById(int)} с аргументом запроса.
     */
    @Test
    public void shouldReturnMessage() throws Exception {
        this.mockMvc.perform(get("/message/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(messageService).findById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения POST запроса с телом запроса содержащим
     * данные нового сообщения для его создания и проверка вызова метода
     * сервисного слоя {@link ImplMessageService#save(Message)}
     * с переданным сообщением в запросе.
     */
    @Test
    public void shouldPostMessage() throws Exception {
        this.mockMvc.perform(post("/message/")
                        .contentType("application/json")
                        .content("{\"content\":\"message\",\"person\":{\"id\":1},"
                                + "\"room\":{\"id\":1}}"))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);

        verify(messageService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getContent()).isEqualTo("message");
        assertThat(argument.getValue().getPerson().getId()).isEqualTo(1);
        assertThat(argument.getValue().getRoom().getId()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения PUT запроса с телом запроса содержащим
     * данные сообщения для его обновления и проверка вызова метода сервисного
     * слоя {@link ru.job4j.chat.service.ImplMessageService#save(Message)}
     * с переданным сообщением в запросе.
     */
    @Test
    public void shouldPutMessage() throws Exception {
        this.mockMvc.perform(put("/message/")
                        .contentType("application/json")
                        .content("{\"content\":\"message\",\"person\":{\"id\":1},"
                                + "\"room\":{\"id\":1}}"))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);

        verify(messageService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getContent()).isEqualTo("message");
        assertThat(argument.getValue().getPerson().getId()).isEqualTo(1);
        assertThat(argument.getValue().getRoom().getId()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения DELETE запроса для удаления сообщения по id,
     * если сообщение найдено и проверка вызова метода сервисного слоя
     * {@link ImplMessageService#delete(Message)} с аргументом запроса.
     */
    @Test
    public void shouldDeleteMessage() throws Exception {
        this.mockMvc.perform(delete("/message/1"))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);

        verify(messageService).delete(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения PATCH запроса для частичного обновления
     * сообщения, если сообщение не найдено и проверка вызова метода сервисного слоя
     * {@link ImplMessageService#patchModel(Message)} с аргументом запроса.
     */
    @Test
    public void shouldPatchMessage() throws Exception {
        this.mockMvc.perform(patch("/message/")
                        .contentType("application/json")
                        .content("{\"content\":\"message\",\"person\":{\"id\":1},"
                                + "\"room\":{\"id\":1}}"))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Message> argument = ArgumentCaptor.forClass(Message.class);

        verify(messageService).patchModel(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getContent()).isEqualTo("message");
        assertThat(argument.getValue().getPerson().getId()).isEqualTo(1);
        assertThat(argument.getValue().getRoom().getId()).isEqualTo(1);
    }
}