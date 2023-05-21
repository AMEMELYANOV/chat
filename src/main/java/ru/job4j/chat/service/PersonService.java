package ru.job4j.chat.service;

import ru.job4j.chat.model.Person;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();

    Optional<Person> findById(int id);

    Person save(Person person);

    void delete(Person person);

    Optional<Person> patchModel(Person person)
            throws InvocationTargetException, IllegalAccessException;

    Person findByUsername(String username);
}