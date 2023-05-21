package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class ImplRoomService implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        return StreamSupport.stream(
                this.roomRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Room> findById(int id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(Room room) {
        roomRepository.delete(room);
    }

    @Override
    public Optional<Room> patchModel(Room room)
            throws InvocationTargetException, IllegalAccessException {
        return DTOService.patchModel(roomRepository, room);
    }
}
