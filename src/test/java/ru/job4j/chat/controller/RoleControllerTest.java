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
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.ImplRoleService;

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
 * @see ru.job4j.chat.controller.RoleController
 */
@SpringBootTest(classes = ChatApplication.class)
@AutoConfigureMockMvc
@WithMockUser
class RoleControllerTest {

    /**
     * Объект заглушка направления запросов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Объект заглушка для ImplRoleService
     */
    @MockBean
    private ImplRoleService roleService;

    /**
     * Выполняется проверка выполнения GET запроса для возвращения
     * списка всех ролей и проверка вызова метода сервисного
     * слоя {@link ImplRoleService#findAll()}.
     */
    @Test
    public void shouldReturnAllRoles() throws Exception {
        this.mockMvc.perform(get("/role/"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(roleService).findAll();
    }

    /**
     * Выполняется проверка выполнения GET запроса для поиска роли по id,
     * если роль не найдена и проверка вызова метода сервисного слоя
     * {@link ImplRoleService#findById(int)} с аргументом запроса.
     */
    @Test
    public void shouldReturnRole() throws Exception {
        this.mockMvc.perform(get("/role/1"))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);

        verify(roleService).findById(argument.capture());
        assertThat(argument.getValue()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения POST запроса с телом запроса содержащим
     * данные новой роли для ее создания и проверка вызова метода
     * сервисного слоя {@link ImplRoleService#save(Role)}
     * с переданной ролью в запросе.
     */
    @Test
    public void shouldPostRole() throws Exception {
        this.mockMvc.perform(post("/role/")
                        .contentType("application/json")
                        .content("{\"name\":\"role\"}"
                        ))
                .andDo(print())
                .andExpect(status().isCreated());

        ArgumentCaptor<Role> argument = ArgumentCaptor.forClass(Role.class);

        verify(roleService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("role");
    }

    /**
     * Выполняется проверка выполнения PUT запроса с телом запроса содержащим
     * данные роли для ее обновления и проверка вызова метода сервисного
     * слоя {@link ImplRoleService#save(Role)} с переданной ролью в запросе.
     */
    @Test
    public void shouldPutRole() throws Exception {
        this.mockMvc.perform(put("/role/")
                        .contentType("application/json")
                        .content("{\"name\":\"role\"}"
                        ))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Role> argument = ArgumentCaptor.forClass(Role.class);

        verify(roleService).save(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("role");
    }

    /**
     * Выполняется проверка выполнения DELETE запроса для удаления роли по id,
     * если роль найдена и проверка вызова метода сервисного слоя
     * {@link ImplRoleService#delete(Role)} с аргументом запроса.
     */
    @Test
    public void shouldDeleteRole() throws Exception {
        this.mockMvc.perform(delete("/role/1"))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Role> argument = ArgumentCaptor.forClass(Role.class);

        verify(roleService).delete(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(1);
    }

    /**
     * Выполняется проверка выполнения PATCH запроса для частичного обновления
     * роли, если роль не найдена и проверка вызова метода сервисного слоя
     * {@link ImplRoleService#patchModel(Role)} с аргументом запроса.
     */
    @Test
    public void shouldPatchRole() throws Exception {
        this.mockMvc.perform(patch("/role/")
                        .contentType("application/json")
                        .content("{\"name\":\"role\"}"
                        ))
                .andDo(print())
                .andExpect(status().isNotFound());

        ArgumentCaptor<Role> argument = ArgumentCaptor.forClass(Role.class);

        verify(roleService).patchModel(argument.capture());
        assertThat(argument.getValue().getId()).isEqualTo(0);
        assertThat(argument.getValue().getName()).isEqualTo("role");
    }
}