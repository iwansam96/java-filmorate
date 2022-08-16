package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.Duration;
import java.time.LocalDate;

@Builder
@Data
public class Film {
    private int id;
    @NotBlank
    private String name;
//    @Pattern(regexp = "^(.{0,200}((?!)|$))")
    @Size(max=200)
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}