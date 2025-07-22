package com.algaworks.algashop.ordering.domain.valueobject;

import java.time.LocalDate;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_IN_THE_FUTURE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_NULL;

public record BirthDate(LocalDate value) {

    public BirthDate(LocalDate value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_NULL);
        if (value.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_IN_THE_FUTURE);
        }
        this.value = value;
    }

    public Integer age() {
        return LocalDate.now().getYear() - this.value.getYear();
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
