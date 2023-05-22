package ru.job4j.chat.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.chat.model.Room;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища комнат
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see RoomRepository
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomRepositoryTest {

    /**
     * Объект для доступа к методам RoomRepository
     */
    @Autowired
    private RoomRepository roomRepository;

    /**
     * Комната
     */
    private Room room;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        room = Room.builder()
                .name("room")
                .build();
    }

    /**
     * Выполняется проверка нахождения в репозитории списка комнат,
     * если комнаты сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnRoomsWhenExists() {
        roomRepository.save(room);

        List<Room> roomsFromDB = StreamSupport.stream(
                this.roomRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());

        assertThat(roomsFromDB.size()).isEqualTo(1);
        assertThat(roomsFromDB.get(0).getName()).isEqualTo(room.getName());
    }

    /**
     * Выполняется проверка нахождения в репозитории комнаты по id,
     * если комната сохранена в репозитории.
     */
    @Test
    public void findByIdShouldReturnRoomWhenExists() {
        int id = roomRepository.save(room).getId();

        Room roomFromDB = roomRepository.findById(id).get();

        assertThat(roomFromDB.getName()).isEqualTo(room.getName());
    }

    /**
     * Выполняется проверка нахождения в репозитории комнаты по id,
     * если комната не сохранена в репозитории.
     */
    @Test
    public void findByIdShouldReturnOptionalEmptyWhenNotExists() {
        Optional<Room> roomFromDB = roomRepository.findById(0);

        assertThat(roomFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка сохранения комнаты в репозитории.
     */
    @Test
    public void saveShouldReturnRoomWhenSuccess() {
        roomRepository.save(room);

        Optional<Room> roomFromDB = roomRepository.findById(room.getId());

        assertThat(roomFromDB.get().getName()).isEqualTo(room.getName());
    }
}