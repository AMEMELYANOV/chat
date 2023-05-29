package ru.job4j.chat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Объект для работы с JSON
     */
    private final ObjectMapper objectMapper;

    /**
     * Выполняет глобальный (уровня приложения) перехват исключений
     * NullPointerException, в случае перехвата, возвращает информацию
     * об исключении через объект response.
     *
     * @param e        перехваченное исключение
     * @param request  запрос пользователя
     * @param response ответ пользователю
     * @throws IOException выброс исключения при работе с системой ввода вывода
     */
    @ExceptionHandler(value = {NullPointerException.class})
    public void handleException(Exception e, HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", "Some of fields empty");
                put("details", e.getMessage());
            }
        }));
        log.error(e.getMessage());
    }

    /**
     * Выполняет глобальный (уровня приложения) перехват исключений валидации моделей
     * MethodArgumentNotValidException, в случае перехвата, возвращает информацию
     * об исключении через объект ResponseEntity.
     *
     * @param e перехваченное исключение
     * @return объект ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(f -> Map.of(
                                f.getField(),
                                String.format("%s. Actual value: %s",
                                        f.getDefaultMessage(), f.getRejectedValue())
                        ))
                        .collect(Collectors.toList())
        );
    }

}