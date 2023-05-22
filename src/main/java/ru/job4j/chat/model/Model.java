package ru.job4j.chat.model;

import lombok.*;

@Getter
@Setter
public abstract class Model {

    @SuppressWarnings("checkstyle:VisibilityModifier")
    protected int id;
}
