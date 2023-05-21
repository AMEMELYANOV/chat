package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ImplRoleService implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return StreamSupport.stream(
                this.roleRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Optional<Role> patchModel(Role role)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(roleRepository, role);
    }
}
