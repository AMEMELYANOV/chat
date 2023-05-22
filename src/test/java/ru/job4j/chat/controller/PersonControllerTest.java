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
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.ImplPersonService;

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
 * @see ru.job4j.chat.controller.PersonController
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class PersonControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Объект заглушка для ImplPersonService
     */
    @MockBean
    private ImplPersonService personService;

    /**
     * Выполняется проверка выполнения GET запроса для возвращения
     * списка всех пользователей и проверка вызова метода сервисного
     * слоя {@link ImplPersonService#findAll()}.
     */
    @Test
    public void shouldReturnAllPersons() throws Exception {
        this.mockMvc.perform(get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(personService).findAll();
    }

    /**
     * Выполняется проверка выполнения GET запроса для поиска пользователя по id,
     * если пользователь не найден и проверка вызова метода сервисного слоя
     * {@link ImplPersonService#findById(int)} с аргументом запроса.
     */
    @Test
    public void shouldReturnPerson() throws Exception {
        this.mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(personService).findById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения POST запроса с телом запроса содержащим
     * данные нового пользователя для его создания и проверка вызова метода
     * сервисного слоя {@link ImplPersonService#save(Person)}
     * с переданным пользователем в запросе.
     */
    @Test
    public void shouldPostPerson() throws Exception {
        this.mockMvc.perform(post("/users/sign-up")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"password\"}"
                        ))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        verify(personService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getUsername()).isEqualTo("user");
    }

    /**
     * Выполняется проверка выполнения PUT запроса с телом запроса содержащим
     * данные пользователя для его обновления и проверка вызова метода сервисного
     * слоя {@link ImplPersonService#save(Person)}
     * с переданным пользователем в запросе.
     */
    @Test
    public void shouldPutPerson() throws Exception {
        this.mockMvc.perform(put("/users/")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"password\"}"
                        ))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        verify(personService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getUsername()).isEqualTo("user");
        assertThat(argument.getValue().getPassword()).isEqualTo("password");
    }

    /**
     * Выполняется проверка выполнения DELETE запроса для удаления пользователя по id,
     * если пользователь найден и проверка вызова метода сервисного слоя
     * {@link ImplPersonService#delete(Person)} с аргументом запроса.
     */
    @Test
    public void shouldDeletePerson() throws Exception {
        this.mockMvc.perform(delete("/users/1"))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        verify(personService).delete(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения PATCH запроса для частичного обновления
     * пользователя, если пользователь не найден и проверка вызова метода сервисного слоя
     * {@link ImplPersonService#patchModel(Person)} с аргументом запроса.
     */
    @Test
    public void shouldPatchPerson() throws Exception {
        this.mockMvc.perform(patch("/users/")
                        .contentType("application/json")
                        .content("{\"username\":\"user\",\"password\":\"pass\"}"
                        ))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        verify(personService).patchModel(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getUsername()).isEqualTo("user");
        assertThat(argument.getValue().getPassword()).isEqualTo("pass");
    }
}