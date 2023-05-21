package ru.job4j.chat.service;

import ru.job4j.chat.model.Room;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Room> findAll();

    Optional<Room> findById(int id);

    Room save(Room room);

    void delete(Room room);

    Optional<Room> patchModel(Room room)
            throws InvocationTargetException, IllegalAccessException;
}
