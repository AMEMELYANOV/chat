package ru.job4j.chat.validator;

/**
 * Класс маркеров групповых операций
 * для валидации моделей
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
public class Operation {

    /**
     * Маркер для валидации операций создания
     */
    public interface OnCreate {
    }

    /**
     * Маркер для валидации операций удаления
     */
    public interface OnDelete {
    }

    /**
     * Маркер для валидации операций обновления
     */
    public interface OnUpdate {
    }
}
