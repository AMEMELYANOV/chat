package ru.job4j.chat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.job4j.chat.ChatApplication;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

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
 * @see ru.job4j.chat.service.ImplPersonService
 */
@SpringBootTest(classes = ChatApplication.class)
class ImplPersonServiceTest {

    /**
     * Объект заглушка для PersonRepository
     */
    @MockBean
    private PersonRepository personRepository;

    /**
     * Объект для доступа к методам PersonService
     */
    private PersonService personService;

    /**
     * Пользователь
     */
    private Person person;

    /**
     * Создает необходимые для выполнения тестов общие объекты.
     * Создание выполняется перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        person = Person.builder()
                .username("person")
                .password("password")
                .build();
//        person.addRole(Role.builder().name("role").build());
        personService = new ImplPersonService(personRepository);
    }

    /**
     * Выполняется проверка нахождения в personRepository списка персон,
     * если персоны сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnPersonsWhenExists() {
        doReturn(List.of(person)).when(personRepository).findAll();

        List<Person> persons = personService.findAll();

        assertThat(persons.size()).isEqualTo(1);
        assertThat(persons.get(0).getUsername()).isEqualTo(person.getUsername());
    }

    /**
     * Выполняется проверка возврата от personRepository пустого списка персон,
     * если персоны не сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnEmptyListWhenNotExists() {
        doReturn(List.of()).when(personRepository).findAll();

        List<Person> persons = personService.findAll();

        assertThat(persons.size()).isEqualTo(0);
    }


    /**
     * Выполняется проверка возвращения персоны при возврате
     * от personRepository, если пользователь найдена по id.
     */
    @Test
    public void findByIdShouldReturnPersonWhenExists() {
        doReturn(Optional.of(person)).when(personRepository).findById(anyInt());

        Optional<Person> personFromDB = personService.findById(anyInt());

        assertThat(personFromDB.get().getUsername()).isEqualTo(person.getUsername());
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от personRepository, если пользователь не найдена по id.
     */
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenNotExists() {
        doReturn(Optional.empty()).when(personRepository).findById(anyInt());

        Optional<Person> personFromDB = personService.findById(anyInt());

        assertThat(personFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка возвращения персоны при возврате
     * от personRepository, если пользователь сохранена.
     */
    @Test
    public void saveShouldReturnPersonWhenSuccess() {
        doReturn(person).when(personRepository).save(person);

        Person personFromDB = personService.save(person);

        assertThat(personFromDB).isEqualTo(person);
    }

    /**
     * Выполняется проверка возвращения пустого Optional при возврате
     * от personRepository, если персоны не существует.
     */
    @Test
    public void patchModelShouldReturnOptionalEmptyWhenNotExists()
            throws InvocationTargetException, IllegalAccessException {
        doReturn(Optional.empty()).when(personRepository).findById(anyInt());

        Optional<Person> personFromDB = personService.patchModel(person);

        assertThat(personFromDB).isEqualTo(Optional.empty());
    }
}