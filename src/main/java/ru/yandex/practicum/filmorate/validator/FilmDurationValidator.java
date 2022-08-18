package ru.yandex.practicum.filmorate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class FilmDurationValidator implements ConstraintValidator<ValidFilmDuration, Duration> {
    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return !value.isNegative();
    }
}
