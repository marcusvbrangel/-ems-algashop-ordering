package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_NULL;

public record Phone(String value) {

    public Phone(String value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_NULL);
        if (value.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_BLANK);
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
