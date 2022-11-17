package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class User {
    @Autowired(required = false)
    private Integer id;

    @Autowired
    @NotBlank
    @Email
    private String email;

    @Autowired
    @NotBlank
    @Pattern(regexp = "^(\\S*((?!)|$))")
    private String login;

    @Autowired
    private String name;

    @Autowired
    @Past
    private LocalDate birthday;

    private Set<Integer> friendsUnconfirmed = new HashSet<>();
    private Set<Integer> friendsConfirmed = new HashSet<>();

//    public User(String email, String login, String name, LocalDate birthday) {
//        this.email = email;
//        this.login = login;
//        this.name = name;
//        this.birthday = birthday;
//    }
//
    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}
