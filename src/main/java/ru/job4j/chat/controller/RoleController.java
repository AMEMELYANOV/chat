package ru.job4j.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;
import ru.job4j.chat.validator.Operation;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для работы с ролями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Role
 */
@AllArgsConstructor
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * Объект для доступа к методам RoleService
     */
    private final RoleService roleService;

    /**
     * Обрабатывает GET запрос, возвращает список ролей. Список
     * получается через метод сервисного слоя {@link RoleService#findAll()}.
     *
     * @return список ролей
     */
    @GetMapping("/")
    public List<Role> findAll() {
        return new ArrayList<>(this.roleService.findAll());
    }

    /**
     * Обрабатывает GET запрос, возвращает ResponseEntity с ролью по переданному
     * идентификатору. Роль получается через метод сервисного слоя
     * {@link RoleService#findById(int)}. Если от сервисного слоя возвращается
     * Optional.empty(), то выбрасывается исключение ResponseStatusException со
     * статусом NOT_FOUND.
     *
     * @param id идентификатор роли
     * @return ResponseEntity с ролью
     */
    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        var role = this.roleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(role.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Role is not found. Please, check id.")));
    }

    /**
     * Обрабатывает POST запрос, создает роль в соответствии с переданным
     * в качестве параметра объектом. Сохранение происходит через метод сервисного
     * слоя {@link RoleService#save(Role)}. При работе метода будет возвращен
     * ResponseEntity со статусом CREATED и объектом сохраненной роли.
     *
     * @param role роль
     * @return ResponseEntity с ролью
     */
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Role> create(@Valid @RequestBody Role role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.roleService.save(role));
    }

    /**
     * Обрабатывает PUT запрос, обновляет роль в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link RoleService#save(Role)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param role роль
     * @return ResponseEntity
     */
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Role role) {
        this.roleService.save(role);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает DELETE запрос, удаляет роль в соответствии с переданным
     * в качестве параметра идентификатором. Удаление происходит через метод сервисного
     * слоя {@link RoleService#delete(Role)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param id роли
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Role role = new Role();
        role.setId(id);
        this.roleService.delete(role);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает PATCH запрос, частично обновляет роль в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link RoleService#patchModel(Role)}. При работе метода будет возвращен
     * ResponseEntity с ролью со статусом OK, если обновление прошло успешно или будет
     * выброшено исключение ResponseStatusException со статусом NOT_FOUND.
     *
     * @param role роль
     * @return ResponseEntity с ролью
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    @PatchMapping("/")
    public ResponseEntity<Role> patch(@RequestBody Role role)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.patchModel(role).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Role is not found or invalid properties mapping")));
    }
}
