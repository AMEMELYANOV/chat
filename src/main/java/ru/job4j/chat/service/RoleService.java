package ru.job4j.chat.service;

import ru.job4j.chat.model.Role;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> findAll();

    Optional<Role> findById(int id);

    Role save(Role role);

    void delete(Role role);

    Optional<Role> patchModel(Role role)
            throws InvocationTargetException, IllegalAccessException;
}
