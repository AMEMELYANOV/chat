package ru.job4j.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Person;
import ru.job4j.chat.repository.PersonRepository;

import static java.util.Collections.emptyList;

/**
 * Реализация сервиса по работе с данными пользователя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Объект для доступа к методам PersonRepository
     */
    private final PersonRepository personRepository;

    /**
     * Выполняет загрузку и возврат пользователя из репозитория.
     * Для загрузки пользователя вызывается метод репозитория
     * {@link PersonRepository#findByUsername(String)}.
     *
     * @param username имя пользователя
     * @return пользователь
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person user = personRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}

