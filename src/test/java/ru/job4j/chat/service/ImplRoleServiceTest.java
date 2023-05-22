package ru.job4j.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.chat.ChatApplication;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

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
 * @see ru.job4j.chat.service.ImplRoleService
 */
@SpringBootTest(classes = ChatApplication.class)
class ImplRoleServiceTest {

    /**
     * Объект заглушка для RoleRepository
     */
    @MockBean
    private RoleRepository roleRepository;

    /**
     * Объект для доступа к методам RoleService
     */
    private RoleService roleService;

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
        roleService = new ImplRoleService(roleRepository);
    }

    /**
     * Выполняется проверка нахождения в roleRepository списка ролей,
     * если роли сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnRolesWhenExists() {
        doReturn(List.of(role)).when(roleRepository).findAll();

        List<Role> roles = roleService.findAll();

        assertThat(roles.size()).isEqualTo(1);
        assertThat(roles.get(0).getName()).isEqualTo(role.getName());
    }

    /**
     * Выполняется проверка возврата от roleRepository пустого списка ролей,
     * если роли не сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnEmptyListWhenNotExists() {
        doReturn(List.of()).when(roleRepository).findAll();

        List<Role> roles = roleService.findAll();

        assertThat(roles.size()).isEqualTo(0);
    }


    /**
     * Выполняется проверка возвращения роли при возврате
     * от roleRepository, если роль найдена по id.
     */
    @Test
    public void findByIdShouldReturnRoleWhenExists() {
        doReturn(Optional.of(role)).when(roleRepository).findById(anyInt());

        Optional<Role> roleFromDB = roleService.findById(anyInt());

        assertThat(roleFromDB.get().getName()).isEqualTo(role.getName());
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от roleRepository, если роль не найдена по id.
     */
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenNotExists() {
        doReturn(Optional.empty()).when(roleRepository).findById(anyInt());

        Optional<Role> roleFromDB = roleService.findById(anyInt());

        assertThat(roleFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка возвращения роли при возврате
     * от roleRepository, если роль сохранена.
     */
    @Test
    public void saveShouldReturnRoleWhenSuccess() {
        doReturn(role).when(roleRepository).save(role);

        Role roleFromDB = roleService.save(role);

        assertThat(roleFromDB).isEqualTo(role);
    }

    /**
     * Выполняется проверка возвращения роли при возврате
     * от roleRepository, если роль обновлена.
     */
    @Test
    public void patchModelShouldReturnOptionalRoleWhenSuccess()
            throws InvocationTargetException, IllegalAccessException {
        doReturn(Optional.of(role)).when(roleRepository).findById(anyInt());

        Optional<Role> roleFromDB = roleService.patchModel(role);

        assertThat(roleFromDB.get()).isEqualTo(role);
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от roleRepository, если роль не существует.
     */
    @Test
    public void patchModelShouldReturnOptionalEmptyWhenNotExists()
            throws InvocationTargetException, IllegalAccessException {
        doReturn(Optional.empty()).when(roleRepository).findById(anyInt());

        Optional<Role> roleFromDB = roleService.patchModel(role);

        assertThat(roleFromDB).isEqualTo(Optional.empty());
    }
}