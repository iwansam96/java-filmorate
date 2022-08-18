package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.util.IdGenerator;
import ru.yandex.practicum.filmorate.validator.ValidFilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
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

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.id = IdGenerator.generateId("film");
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}
