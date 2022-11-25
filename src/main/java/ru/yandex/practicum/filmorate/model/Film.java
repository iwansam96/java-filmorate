package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.validator.ValidFilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class Film {
    private int id;

    @NotBlank
    private String name;

    @Size(max=200)
    private String description;

    @ValidFilmReleaseDate
    private LocalDate releaseDate;

    @Positive
    private int duration;

    private Set<Integer> likes;
    private Collection<Genre> genres;

    @NotNull
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
