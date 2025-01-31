package ru.job4j.chat.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.job4j.chat.model.Person;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Фильтр пользователя
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * Секретное слово
     */
    public static final String SECRET = "SecretKeyToGenJWTs";

    /**
     * Время действия токена
     */
    public static final long EXPIRATION_TIME = 864_000_000; /* 10 days */

    /**
     * Префикс токена
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * Строка заголовка
     */
    public static final String HEADER_STRING = "Authorization";

    /**
     * Путь регистрации
     */
    public static final String SIGN_UP_URL = "/users/sign-up";

    /**
     * Менеджер аутентификации
     */
    private final AuthenticationManager auth;

    /**
     * Создает пользовательский объект с данными сайта, который
     * пытается залогиниться, возвращает объект Authentication.
     *
     * @param req запрос пользователя
     * @param res ответ пользователю
     * @return объект аутентификации
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            Person creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Person.class);

            return auth.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Создает JWT токен и добавляет его в response.
     * Вызов метода происходит, когда пользователь
     * авторизовался.
     *
     * @param req   запрос пользователя
     * @param res   ответ пользователю
     * @param chain цепь фильтров
     * @param auth  объект аутентификации
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth)
            throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
