package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Model;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Реализация сервиса по работе с комнатами
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see ru.job4j.chat.service.RoomService
 */
@AllArgsConstructor
@Service
public class ImplRoomService implements RoomService {

    /**
     * Объект для доступа к методам RoomRepository
     */
    private final RoomRepository roomRepository;

    /**
     * Возвращает список комнат. Выполняет вызов метода
     * {@link RoomRepository#findAll()} для получения списка
     * комнат.
     *
     * @return список комнат
     */
    @Override
    public List<Room> findAll() {
        return StreamSupport.stream(
                this.roomRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    /**
     * Возвращает Optional от комнаты по аргументу id.
     * Для получения комнаты вызывается метод репозитория
     * {@link RoomRepository#findById(Object)}.
     *
     * @param id идентификатор комнаты
     * @return найденная комната
     */
    @Override
    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    /**
     * Сохраняет комнату в репозитории.
     * Для сохранения комнаты вызывается метод репозитория
     * {@link RoomRepository#save(Object)}.
     *
     * @param room комната
     * @return сохраненная комната
     */
    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Удаляет комнату в репозитории.
     * Для удаления комнаты вызывается метод репозитория
     * {@link RoomRepository#delete(Object)}.
     *
     * @param room комната
     */
    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }

    /**
     * Обновляет комнату в репозитории.
     * Для обновления комнаты вызывается метод сервиса
     * {@link DTOService#patchModel(CrudRepository, Model)}.
     *
     * @param room комната
     * @return обновленная комната
     * @throws InvocationTargetException при выбросе исключения при работе с рефлексией
     * @throws IllegalAccessException    при невозможности вызвать метод объекта
     */
    @Override
    public Optional<Room> patchModel(Room room)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(roomRepository, room);
    }
}
