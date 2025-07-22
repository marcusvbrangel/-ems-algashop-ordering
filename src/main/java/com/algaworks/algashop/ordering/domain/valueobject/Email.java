package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public record Email(String value) {

    public Email(String value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_EMAIL_CANNOT_BE_NULL);
        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_EMAIL_CANNOT_BE_BLANK);
        }
        FieldValidations.requireValidEmail(value, VALIDATION_ERROR_EMAIL_IS_NOT_VALID);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
