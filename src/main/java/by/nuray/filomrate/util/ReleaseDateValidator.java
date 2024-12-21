package by.nuray.filomrate.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ValidReleaseDate, LocalDate> {


    private static final LocalDate MIN_RELEASE_DATE =LocalDate.of(1895, 12, 28);
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {

        if (date == null) {
            return true;
        }
        return !date.isBefore(MIN_RELEASE_DATE);

    }
}
