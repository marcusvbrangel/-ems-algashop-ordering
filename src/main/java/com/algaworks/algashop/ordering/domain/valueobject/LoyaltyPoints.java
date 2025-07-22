package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

public record LoyaltyPoints(Integer value) implements Comparable<LoyaltyPoints> {

    public LoyaltyPoints() {
        this(0);
    }

    public LoyaltyPoints(Integer value) {
        Objects.requireNonNull(value);
        if (value < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative");
        }
        this.value = value;
    }

    public LoyaltyPoints add(Integer value) {
        return add(new LoyaltyPoints(value));
    }

    public LoyaltyPoints add(LoyaltyPoints loyaltyPonts) {
        Objects.requireNonNull(loyaltyPonts);
        if (loyaltyPonts.value() < 0) {
            throw new IllegalArgumentException("Loyalty points cannot be negative");
        }
        return new LoyaltyPoints(this.value() + loyaltyPonts.value());
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
