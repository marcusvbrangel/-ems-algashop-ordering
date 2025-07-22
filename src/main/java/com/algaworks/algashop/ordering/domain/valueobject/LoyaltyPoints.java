package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public record LoyaltyPoints(Integer value) implements Comparable<LoyaltyPoints> {

    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints(Integer value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NULL);
        if (value < 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NEGATIVE);
        }
        this.value = value;
    }

    public LoyaltyPoints add(Integer value) {
        return add(new LoyaltyPoints(value));
    }

    public LoyaltyPoints add(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints, VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NULL);
        if (loyaltyPoints.value() <= 0) {
            throw new IllegalArgumentException(VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NEGATIVE_OR_ZERO);
        }
        return new LoyaltyPoints(this.value() + loyaltyPoints.value());
    }

    @Override
    public String toString() {
        return value.toString();
    }


    @Override
    public int compareTo(LoyaltyPoints o) {
        return this.value().compareTo(o.value());
    }

}
