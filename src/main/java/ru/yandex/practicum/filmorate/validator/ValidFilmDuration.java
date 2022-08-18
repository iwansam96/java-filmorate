package ru.yandex.practicum.filmorate.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FilmDurationValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFilmDuration {

    String message() default "Film duration must be positive";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}