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
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.ImplRoomService;

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
 * @see ru.job4j.chat.controller.RoomController
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class RoomControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Объект заглушка для ImplRoomService
     */
    @MockBean
    private ImplRoomService roomService;

    /**
     * Выполняется проверка выполнения GET запроса для возвращения
     * списка всех комнат и проверка вызова метода сервисного
     * слоя {@link ImplRoomService#findAll()}.
     */
    @Test
    public void shouldReturnAllRoles() throws Exception {
        this.mockMvc.perform(get("/room/"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(roomService).findAll();
    }

    /**
     * Выполняется проверка выполнения GET запроса для поиска комнаты по id,
     * если комната не найдена и проверка вызова метода сервисного слоя
     * {@link ImplRoomService#findById(int)} с аргументом запроса.
     */
    @Test
    public void shouldReturnRole() throws Exception {
        this.mockMvc.perform(get("/room/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(roomService).findById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения POST запроса с телом запроса содержащим
     * данные новой комнаты для ее создания и проверка вызова метода
     * сервисного слоя {@link ImplRoomService#save(Room)}
     * с переданной комнатой в запросе.
     */
    @Test
    public void shouldPostRole() throws Exception {
        this.mockMvc.perform(post("/room/")
                        .contentType("application/json")
                        .content("{\"name\":\"room\"}"
                        ))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);

        verify(roomService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("room");
    }

    /**
     * Выполняется проверка выполнения PUT запроса с телом запроса содержащим
     * данные комнаты для ее обновления и проверка вызова метода сервисного
     * слоя {@link ImplRoomService#save(Room)} с переданной комнатой в запросе.
     */
    @Test
    public void shouldPutRole() throws Exception {
        this.mockMvc.perform(put("/room/")
                        .contentType("application/json")
                        .content("{\"name\":\"room\"}"
                        ))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);

        verify(roomService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("room");
    }

    /**
     * Выполняется проверка выполнения DELETE запроса для удаления комнаты по id,
     * если комната найдена и проверка вызова метода сервисного слоя
     * {@link ImplRoomService#delete(Room)} с аргументом запроса.
     */
    @Test
    public void shouldDeleteRole() throws Exception {
        this.mockMvc.perform(delete("/room/1"))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);

        verify(roomService).delete(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения PATCH запроса для частичного обновления
     * комнаты, если комната не найдена и проверка вызова метода сервисного слоя
     * {@link ImplRoomService#patchModel(Room)} с аргументом запроса.
     */
    @Test
    public void shouldPatchRole() throws Exception {
        this.mockMvc.perform(patch("/room/")
                        .contentType("application/json")
                        .content("{\"name\":\"room\"}"
                        ))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Room> argument = ArgumentCaptor.forClass(Room.class);

        verify(roomService).patchModel(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("room");
    }
}