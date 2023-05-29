package ru.job4j.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.service.PersonService;
import ru.job4j.chat.validator.Operation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Контроллер для работы с пользователями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Person
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class PersonController {

    /**
     * Объект для доступа к методам PersonService
     */
    private final PersonService personService;

    /**
     * Объект шифратор паролей
     */
    private final BCryptPasswordEncoder encoder;

    /**
     * Объект для работы с JSON
     */
    private final ObjectMapper objectMapper;

    /**
     * Обрабатывает GET запрос, возвращает список пользователей. Список
     * получается через метод сервисного слоя {@link PersonService#findAll()}.
     *
     * @return список пользователей
     */
    @GetMapping("/all")
    public List<Person> findAll() {
        return new ArrayList<>(this.personService.findAll());
    }

    /**
     * Обрабатывает GET запрос, возвращает ResponseEntity с пользователем по переданному
     * идентификатору. Пользователь получается через метод сервисного слоя
     * {@link PersonService#findById(int)}. Если от сервисного слоя возвращается
     * Optional.empty(), то выбрасывается исключение ResponseStatusException со
     * статусом NOT_FOUND.
     *
     * @param id идентификатор пользователя
     * @return ResponseEntity с пользователем
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        var person = this.personService.findById(id);
        return new ResponseEntity<Person>(
                person.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Person is not found. Please, check id.")
                ), HttpStatus.OK
        );
    }

    /**
     * Обрабатывает POST запрос, создает пользователя в соответствии с переданным
     * в качестве параметра объектом. Сохранение происходит через метод сервисного
     * слоя {@link PersonService#save(Person)}. Если пользователь с таким именем
     * уже зарегистрировался, будет возвращен ResponseEntity со статусом BAD_REQUEST,
     * иначе ResponseEntity со статусом CREATED и объектом зарегистрированного
     * пользователя.
     *
     * @param person пользователь
     * @return ResponseEntity с пользователем
     */
    @PostMapping("/sign-up")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        Person personFromDB = personService.findByUsername(person.getUsername());
        if (personFromDB != null) {
            return new ResponseEntity<Person>(
                    person,
                    HttpStatus.BAD_REQUEST
            );
        }
        person.setPassword(encoder.encode(person.getPassword()));
        return new ResponseEntity<Person>(
                this.personService.save(person),
                HttpStatus.CREATED
        );
    }

    /**
     * Обрабатывает PUT запрос, обновляет пользователя в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link PersonService#save(Person)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param person пользователь
     * @return ResponseEntity
     */
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Person person) {
        this.personService.save(person);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает DELETE запрос, удаляет пользователя в соответствии с переданным
     * в качестве параметра идентификатором. Удаление происходит через метод сервисного
     * слоя {@link PersonService#delete(Person)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param id пользователя
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        this.personService.delete(person);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает PATCH запрос, частично обновляет пользователя в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link PersonService#patchModel(Person)}. При работе метода будет возвращен
     * ResponseEntity с пользователем со статусом OK, если обновление прошло успешно или будет
     * выброшено исключение ResponseStatusException со статусом NOT_FOUND.
     *
     * @param person пользователь
     * @return ResponseEntity с пользователем
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    @PatchMapping("/")
    public ResponseEntity<Person> patch(@RequestBody Person person)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.patchModel(person).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Person is not found or invalid properties mapping")));
    }

    /**
     * Выполняет локальный (уровня контроллера) перехват исключений
     * IllegalArgumentException, в случае перехвата, возвращает информацию
     * об исключении через объект response.
     *
     * @param e        перехваченное исключение
     * @param request  запрос пользователя
     * @param response ответ пользователю
     * @throws IOException выброс исключения при работе с системой ввода вывода
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request,
                                 HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", e.getMessage());
                put("type", e.getClass());
            }
        }));
        log.error(e.getLocalizedMessage());
    }
}

