package ru.job4j.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;
import ru.job4j.chat.validator.Operation;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для работы с сообщениями
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Message
 */
@AllArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    /**
     * Объект для доступа к методам MessageService
     */
    private final MessageService messageService;

    /**
     * Обрабатывает GET запрос, возвращает список сообщений. Список
     * получается через метод сервисного слоя {@link MessageService#findAll()}.
     *
     * @return список сообщений
     */
    @GetMapping("/")
    public List<Message> findAll() {
        return new ArrayList<>(this.messageService.findAll());
    }

    /**
     * Обрабатывает GET запрос, возвращает ResponseEntity с сообщением по переданному
     * идентификатору. Сообщение получается через метод сервисного слоя
     * {@link MessageService#findById(int)}. Если от сервисного слоя возвращается
     * Optional.empty(), то выбрасывается исключение ResponseStatusException со
     * статусом NOT_FOUND.
     *
     * @param id идентификатор сообщения
     * @return ResponseEntity с сообщением
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        var message = this.messageService.findById(id);
        return new ResponseEntity<Message>(
                message.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Message is not found. Please, check id.")
                ), HttpStatus.OK
        );
    }

    /**
     * Обрабатывает POST запрос, создает сообщение в соответствии с переданным
     * в качестве параметра объектом. Сохранение происходит через метод сервисного
     * слоя {@link MessageService#save(Message)}. При работе метода будет возвращен
     * ResponseEntity со статусом CREATED и объектом сохраненного сообщения.
     *
     * @param message сообщение
     * @return ResponseEntity с сообщением
     */
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Message> create(@Valid @RequestBody Message message) {
        return new ResponseEntity<Message>(
                this.messageService.save(message),
                HttpStatus.CREATED
        );
    }

    /**
     * Обрабатывает PUT запрос, обновляет сообщение в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link MessageService#save(Message)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param message сообщение
     * @return ResponseEntity
     */
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Message message) {
        this.messageService.save(message);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает DELETE запрос, удаляет сообщение в соответствии с переданным
     * в качестве параметра идентификатором. Удаление происходит через метод сервисного
     * слоя {@link MessageService#delete(Message)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param id сообщения
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message();
        message.setId(id);
        this.messageService.delete(message);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает PATCH запрос, частично обновляет пользователя в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link MessageService#patchModel(Message)}. При работе метода будет возвращен
     * ResponseEntity с сообщением со статусом OK, если обновление прошло успешно или будет
     * выброшено исключение ResponseStatusException со статусом NOT_FOUND.
     *
     * @param message сообщение
     * @return ResponseEntity с сообщением
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    @PatchMapping("/")
    public ResponseEntity<Message> patch(@RequestBody Message message)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(messageService.patchModel(message).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Message is not found or invalid properties mapping")));
    }
}
