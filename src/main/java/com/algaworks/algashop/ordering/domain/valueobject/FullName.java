package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FULL_NAME_CANNOT_BE_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_FULL_NAME_CANNOT_BE_NULL;

public record FullName(String firstName, String lastName) {

    public FullName(String firstName, String lastName) {

        Objects.requireNonNull(firstName, VALIDATION_ERROR_FULL_NAME_CANNOT_BE_NULL);
        Objects.requireNonNull(lastName, VALIDATION_ERROR_FULL_NAME_CANNOT_BE_NULL);

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException(VALIDATION_ERROR_FULL_NAME_CANNOT_BE_BLANK);
        }

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();

    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
