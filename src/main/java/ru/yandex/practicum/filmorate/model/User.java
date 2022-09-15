package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Integer id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^(\\S*((?!)|$))")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    private Set<Integer> friendsUnconfirmed;
    private Set<Integer> friendsConfirmed;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friendsUnconfirmed = new HashSet<>();
    }
}
