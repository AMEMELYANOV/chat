package ru.job4j.chat.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель данных идентификатор
 *
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Getter
@Setter
public abstract class Model {

    /**
     * Идентификатор
     */
    protected int id;
}
