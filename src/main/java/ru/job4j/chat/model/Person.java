package ru.job4j.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.chat.validator.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "person")
public class Person extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private int id;

    @Size(min = 3, message = "Username's length must be more than 2 characters.")
    @NotBlank(message = "Title must be not empty")
    private String username;

    @Size(min = 6, message = "Password's length must be more than 5 characters")
    @NotBlank(message = "Title must be not empty")
    private String password;

    @ManyToMany
    private final List<Role> roles = new ArrayList<>();

}