package ru.job4j.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.chat.ChatApplication;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.repository.RoomRepository;

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
 * @see ru.job4j.chat.service.ImplRoomService
 */
@SpringBootTest(classes = ChatApplication.class)
class ImplRoomServiceTest {

    /**
     * Объект заглушка для RoomRepository
     */
    @MockBean
    private RoomRepository roomRepository;

    /**
     * Объект для доступа к методам RoomService
     */
    private RoomService roomService;

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
        roomService = new ImplRoomService(roomRepository);
    }

    /**
     * Выполняется проверка нахождения в roomRepository списка комнат,
     * если комнаты сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnRoomsWhenExists() {
        doReturn(List.of(room)).when(roomRepository).findAll();

        List<Room> rooms = roomService.findAll();

        assertThat(rooms.size()).isEqualTo(1);
        assertThat(rooms.get(0).getName()).isEqualTo(room.getName());
    }

    /**
     * Выполняется проверка возврата от roomRepository пустого списка комнат,
     * если комнаты не сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnEmptyListWhenNotExists() {
        doReturn(List.of()).when(roomRepository).findAll();

        List<Room> rooms = roomService.findAll();

        assertThat(rooms.size()).isEqualTo(0);
    }


    /**
     * Выполняется проверка возвращения комнаты при возврате
     * от roomRepository, если комната найдена по id.
     */
    @Test
    public void findByIdShouldReturnRoomWhenExists() {
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());

        Optional<Room> roomFromDB = roomService.findById(anyInt());

        assertThat(roomFromDB.get().getName()).isEqualTo(room.getName());
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от roomRepository, если комната не найдена по id.
     */
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenNotExists() {
        doReturn(Optional.empty()).when(roomRepository).findById(anyInt());

        Optional<Room> roomFromDB = roomService.findById(anyInt());

        assertThat(roomFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка возвращения комнаты при возврате
     * от roomRepository, если комната сохранена.
     */
    @Test
    public void saveShouldReturnRoomWhenSuccess() {
        doReturn(room).when(roomRepository).save(room);

        Room roomFromDB = roomService.save(room);

        assertThat(roomFromDB).isEqualTo(room);
    }

    /**
     * Выполняется проверка возвращения комнаты при возврате
     * от roomRepository, если комната обновлена.
     */
    @Test
    public void patchModelShouldReturnOptionalRoomWhenSuccess()
            throws InvocationTargetException, IllegalAccessException {
        doReturn(Optional.of(room)).when(roomRepository).findById(anyInt());

        Optional<Room> roomFromDB = roomService.patchModel(room);

        assertThat(roomFromDB.get()).isEqualTo(room);
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от roomRepository, если комната не существует.
     */
    @Test
    public void patchModelShouldReturnOptionalEmptyWhenNotExists()
            throws InvocationTargetException, IllegalAccessException {
        doReturn(Optional.empty()).when(roomRepository).findById(anyInt());

        Optional<Room> roomFromDB = roomService.patchModel(room);

        assertThat(roomFromDB).isEqualTo(Optional.empty());
    }
}