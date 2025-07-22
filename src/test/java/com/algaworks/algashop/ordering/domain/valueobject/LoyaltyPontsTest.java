package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void should_generate_with_value() {
        LoyaltyPoints loyaltyPonts = new LoyaltyPoints(10);
        Assertions.assertThat(loyaltyPonts.value()).isEqualTo(10);
    }

    @Test
    void should_add_value() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        LoyaltyPoints loyaltyPointUpdated = loyaltyPoints.add(5);
        Assertions.assertThat(loyaltyPointUpdated.value()).isEqualTo(15);
    }

    @Test
    void should_not_add_value() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> loyaltyPoints.add(-1));
    }

}