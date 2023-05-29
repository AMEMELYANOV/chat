package ru.job4j.chat.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.job4j.chat.filter.JWTAuthenticationFilter;
import ru.job4j.chat.filter.JWTAuthorizationFilter;
import ru.job4j.chat.service.UserDetailsServiceImpl;

import static ru.job4j.chat.filter.JWTAuthenticationFilter.SIGN_UP_URL;

/**
 * Конфигурация системы безопасности
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    /**
     * Объект для доступа к методам UserDetailsServiceImpl
     */
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Шифратор паролей
     */
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Создает конфигурацию авторизации при работе с приложением.
     *
     * @param http объект HttpSecurity для которого выполняется настройка авторизации
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                /* this disables session creation on Spring Security */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Создание менеджера аутентификации.
     *
     * @param auth конфигурация аутентификации
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Создание объекта конфигурации CORS фильтра для Spring Security
     *
     * @return конфигурация CORS фильтра
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

