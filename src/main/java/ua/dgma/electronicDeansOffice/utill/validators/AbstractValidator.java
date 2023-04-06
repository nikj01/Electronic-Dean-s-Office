package ua.dgma.electronicDeansOffice.utill.validators;

import org.springframework.validation.Errors;

public interface AbstractValidator {
    void validate(Object target);
}
