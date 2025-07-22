package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

class CustomerTest {

    @Test
    void given_invalid_email_when_creating_customer_then_should_generate_exception() {

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    new Customer(
                            IdGenerator.generateTimeBasedUUID(),
                            "John Doe",
                            LocalDate.of(1991, 7, 5),
                            "invalid-email",
                            "123456789",
                            "12345678901",
                            false,
                            OffsetDateTime.now()
                    );
                })
                .withMessage(VALIDATION_ERROR_EMAIL_IS_NOT_VALID);

    }

    @Test
    void given_invalid_email_when_updating_customer_email_then_should_generate_exception() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "123456789",
                "12345678901",
                false,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    customer.changeEmail("invalid-email");
                })
                .withMessage(VALIDATION_ERROR_EMAIL_IS_NOT_VALID);

    }

    @Test
    void given_unarchived_customer_when_archive_then_should_anonymize() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "123456789",
                "12345678901",
                false,
                OffsetDateTime.now()
        );

        customer.archive();

        Assertions.assertWith(customer,
                c -> Assertions.assertThat(c.fullName()).isEqualTo("Anonymous"),
                c -> Assertions.assertThat(c.email()).isNotEqualTo("john.doe@gmail.com"),
                c -> Assertions.assertThat(c.phone()).isEqualTo("000-000-0000"),
                c -> Assertions.assertThat(c.document()).isEqualTo("000-00-0000"),
                c -> Assertions.assertThat(c.birthDate()).isNull()
        );

    }

    @Test
    void given_archived_customer_when_try_to_update_then_should_generate_exception() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "Anonymous",
                null,
                "anonymous@gmail.com",
                "000-000-0000",
                "000-00-0000",
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                10
        );

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive)
                .withMessage(VALIDATION_ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail("newmail@gmail.com"))
                .withMessage(VALIDATION_ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone("123-123-1111"))
                .withMessage(VALIDATION_ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications)
                .withMessage(VALIDATION_ERROR_CUSTOMER_ARCHIVED);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.disablePromotionNotifications())
                .withMessage(VALIDATION_ERROR_CUSTOMER_ARCHIVED);

    }

    @Test
    void given_brand_new_customer_when_add_loyalty_points_then_should_sum_points() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "123456789",
                "12345678901",
                false,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(10);
        customer.addLoyaltyPoints(20);

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(30);

    }

    @Test
    void given_brand_new_customer_when_add_invalid_loyalty_points_then_should_generate_exception() {

        Customer customer = new Customer(
                IdGenerator.generateTimeBasedUUID(),
                "John Doe",
                LocalDate.of(1991, 7, 5),
                "john.doe@gmail.com",
                "123456789",
                "12345678901",
                false,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(0))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NEGATIVE_OR_ZERO);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(-2))
                .withMessage(VALIDATION_ERROR_LOYALTY_POINTS_CANNOT_BE_NEGATIVE_OR_ZERO);

    }

}
