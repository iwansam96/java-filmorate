package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.validator.ValidFilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {
    @Autowired(required = false)
    private int id;

    @Autowired
    @NotBlank
    private String name;

    @Autowired(required = false)
    @Size(max=200)
    private String description;

    @Autowired
    @ValidFilmReleaseDate
    private LocalDate releaseDate;

    @Autowired
    @Positive
    private int duration;

    @Autowired(required = false)
    private Set<Integer> likes;

    private Collection<Genre> genres;
    private Mpa mpa;


    public Film(int id, String name, String description, LocalDate releaseDate, int duration, Set<Integer> likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
    }
}
