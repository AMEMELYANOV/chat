package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Реализация сервиса по работе с ролями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.service.RoleService
 */
@AllArgsConstructor
@Service
public class ImplRoleService implements RoleService {

    /**
     * Объект для доступа к методам RoleRepository
     */
    private final RoleRepository roleRepository;

    /**
     * Возвращает список ролей. Выполняет вызов метода
     * {@link RoleRepository#findAll()} для получения списка
     * ролей.
     *
     * @return список ролей
     */
    @Override
    public List<Role> findAll() {
        return StreamSupport.stream(
                this.roleRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    /**
     * Возвращает Optional от роли по аргументу id.
     * Для получения роли вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор роли
     * @return найденная роль
     */
    @Override
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    /**
     * Сохраняет роль в репозитории.
     * Для сохранения роли вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param role роль
     * @return сохраненная роль
     */
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Удаляет роль в репозитории.
     * Для удаления роли вызывается метод репозитория
     * {@link RoleRepository#delete(Object)}.
     *
     * @param role роль
     */
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

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
    @Override
    public Optional<Role> patchModel(Role role)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(roleRepository, role);
    }
}
