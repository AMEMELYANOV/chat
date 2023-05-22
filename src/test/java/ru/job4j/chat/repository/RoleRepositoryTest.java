package ru.job4j.chat.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.chat.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища ролей
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see RoleRepository
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    /**
     * Объект для доступа к методам RoleRepository
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Роль
     */
    private Role role;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        role = Role.builder()
                .name("role")
                .build();
    }

    /**
     * Выполняется проверка нахождения в репозитории списка ролей,
     * если роли сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnRolesWhenExists() {
        roleRepository.save(role);

        List<Role> rolesFromDB = StreamSupport.stream(
                this.roleRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());

        assertThat(rolesFromDB.size()).isEqualTo(1);
        assertThat(rolesFromDB.get(0).getName()).isEqualTo(role.getName());
    }

    /**
     * Выполняется проверка нахождения в репозитории роли по id,
     * если роль сохранена в репозитории.
     */
    @Test
    public void findByIdShouldReturnRoleWhenExists() {
        int id = roleRepository.save(role).getId();

        Role roleFromDB = roleRepository.findById(id).get();

        assertThat(roleFromDB.getName()).isEqualTo(role.getName());
    }

    /**
     * Выполняется проверка нахождения в репозитории роли по id,
     * если роль не сохранена в репозитории.
     */
    @Test
    public void findByIdShouldReturnOptionalEmptyWhenNotExists() {
        Optional<Role> roleFromDB = roleRepository.findById(0);

        assertThat(roleFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка сохранения роли в репозитории.
     */
    @Test
    public void saveShouldReturnRoleWhenSuccess() {
        roleRepository.save(role);

        Optional<Role> roleFromDB = roleRepository.findById(role.getId());

        assertThat(roleFromDB.get().getName()).isEqualTo(role.getName());
    }
}