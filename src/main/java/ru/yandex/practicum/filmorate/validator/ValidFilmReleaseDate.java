package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmReleaseDateValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFilmReleaseDate {

    String message() default "Release Date annot be earlier than 28.12.1895";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}