package ru.job4j.chat.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;
import ru.job4j.chat.validator.Operation;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для работы с комнатами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.model.Room
 */
@AllArgsConstructor
@RestController
@RequestMapping("/room")
public class RoomController {

    /**
     * Объект для доступа к методам RoomService
     */
    private final RoomService roomService;

    /**
     * Обрабатывает GET запрос, возвращает список комнат. Список
     * получается через метод сервисного слоя {@link RoomService#findAll()}.
     *
     * @return список комнат
     */
    @GetMapping("/")
    public List<Room> findAll() {
        return new ArrayList<>(this.roomService.findAll());
    }

    /**
     * Обрабатывает GET запрос, возвращает ResponseEntity с комнатой по переданному
     * идентификатору. Комната получается через метод сервисного слоя
     * {@link RoomService#findById(int)}. Если от сервисного слоя возвращается
     * Optional.empty(), то выбрасывается исключение ResponseStatusException со
     * статусом NOT_FOUND.
     *
     * @param id идентификатор комнаты
     * @return ResponseEntity с комнатой
     */
    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var room = this.roomService.findById(id);
        return new ResponseEntity<Room>(
                room.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Room is not found. Please, check id.")
                ), HttpStatus.OK
        );
    }

    /**
     * Обрабатывает POST запрос, создает комнату в соответствии с переданным
     * в качестве параметра объектом. Сохранение происходит через метод сервисного
     * слоя {@link RoomService#save(Room)}. При работе метода будет возвращен
     * ResponseEntity со статусом CREATED и объектом сохраненной комнаты.
     *
     * @param room комната
     * @return ResponseEntity с комнатой
     */
    @PostMapping("/")
    @Validated(Operation.OnCreate.class)
    public ResponseEntity<Room> create(@Valid @RequestBody Room room) {
        return new ResponseEntity<Room>(
                this.roomService.save(room),
                HttpStatus.CREATED
        );
    }

    /**
     * Обрабатывает PUT запрос, обновляет комнату в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link RoomService#save(Room)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param room комната
     * @return ResponseEntity
     */
    @PutMapping("/")
    @Validated(Operation.OnUpdate.class)
    public ResponseEntity<Void> update(@Valid @RequestBody Room room) {
        this.roomService.save(room);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает DELETE запрос, удаляет комнату в соответствии с переданным
     * в качестве параметра идентификатором. Удаление происходит через метод сервисного
     * слоя {@link RoomService#delete(Room)}. При работе метода будет возвращен
     * ResponseEntity со статусом OK.
     *
     * @param id комнаты
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        this.roomService.delete(room);
        return ResponseEntity.ok().build();
    }

    /**
     * Обрабатывает PATCH запрос, частично обновляет комнату в соответствии с переданным
     * в качестве параметра объектом. Обновление происходит через метод сервисного
     * слоя {@link RoomService#patchModel(Room)}. При работе метода будет возвращен
     * ResponseEntity с комнатой со статусом OK, если обновление прошло успешно или будет
     * выброшено исключение ResponseStatusException со статусом NOT_FOUND.
     *
     * @param room комната
     * @return ResponseEntity с комнатой
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    @PatchMapping("/")
    public ResponseEntity<Room> patch(@RequestBody Room room)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomService.patchModel(room).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Room is not found or invalid properties mapping")));
    }
}
