package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.validator.ValidFilmDuration;
import ru.yandex.practicum.filmorate.validator.ValidFilmReleaseDate;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Builder
@Data
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(max=200)
    private String description;
    @ValidFilmReleaseDate
    private LocalDate releaseDate;
    @ValidFilmDuration
    private Duration duration;
}
