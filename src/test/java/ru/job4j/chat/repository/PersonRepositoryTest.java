package ru.job4j.chat.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j.chat.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тест класс реализации хранилища персон
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see PersonRepository
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest {

    /**
     * Объект для доступа к методам PersonRepository
     */
    @Autowired
    private PersonRepository personRepository;

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
                .username("username")
                .password("password")
                .build();
    }

    /**
     * Выполняется проверка нахождения в репозитории списка персон,
     * если персоны сохранены в репозитории.
     */
    @Test
    public void findAllShouldReturnPersonsWhenExists() {
        personRepository.save(person);

        List<Person> personsFromDB = StreamSupport.stream(
                this.personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());

        assertThat(personsFromDB.size()).isEqualTo(1);
        assertThat(personsFromDB.get(0).getUsername()).isEqualTo(person.getUsername());
    }

    /**
     * Выполняется проверка нахождения в репозитории персоны по id,
     * если пользователь сохранена в репозитории.
     */
    @Test
    public void findByIdShouldReturnPersonWhenExists() {
        int id = personRepository.save(person).getId();

        Person personFromDB = personRepository.findById(id).get();

        assertThat(personFromDB.getUsername()).isEqualTo(person.getUsername());
    }

    /**
     * Выполняется проверка нахождения в репозитории персоны по id,
     * если персона не сохранена в репозитории.
     */
    @Test
    public void findByIdShouldReturnOptionalEmptyWhenNotExists() {
        Optional<Person> personFromDB = personRepository.findById(1);

        assertThat(personFromDB).isEqualTo(Optional.empty());
    }

    /**
     * Выполняется проверка сохранения персоны в репозитории.
     */
    @Test
    public void saveShouldReturnPersonWhenSuccess() {
        personRepository.save(person);

        Optional<Person> personFromDB = personRepository.findById(person.getId());

        assertThat(personFromDB.get().getUsername()).isEqualTo(person.getUsername());
    }
}