package ru.job4j.chat.service;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса по работе с ролями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.service.RoleService
 */
public interface RoleService {

    /**
     * Возвращает список ролей. Выполняет вызов метода
     * {@link RoleRepository#findAll()} для получения списка
     * ролей.
     *
     * @return список ролей
     */
    List<Role> findAll();

    /**
     * Возвращает Optional от роли по аргументу id.
     * Для получения роли вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор роли
     * @return найденная роль
     */
    Optional<Role> findById(int id);

    /**
     * Сохраняет роль в репозитории.
     * Для сохранения роли вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param role роль
     * @return сохраненная роль
     */
    Role save(Role role);

    /**
     * Удаляет роль в репозитории.
     * Для удаления роли вызывается метод репозитория
     * {@link RoleRepository#delete(Object)}.
     *
     * @param role роль
     */
    void delete(Role role);

    /**
     * Обновляет роль в репозитории.
     * Для обновления роли вызывается метод сервиса
     * {@link DTOService#patchModel(CrudRepository, Model)}.
     *
     * @param role роль
     * @return обновленная роль
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    Optional<Role> patchModel(Role role)
            throws InvocationTargetException, IllegalAccessException;
}
