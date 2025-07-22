package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void deve_criar_email_valido() {
        Email email = new Email("usuario@email.com");
        assertEquals("usuario@email.com", email.value());
    }

    @Test
    void deve_lancar_excecao_para_email_nulo() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Email(null));
        assertEquals(VALIDATION_ERROR_EMAIL_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void deve_lancar_excecao_para_email_em_branco() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Email("   "));
        assertEquals(VALIDATION_ERROR_EMAIL_CANNOT_BE_BLANK, exception.getMessage());
    }

    @Test
    void deve_lancar_excecao_para_email_invalido() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Email("email-invalido"));
        assertEquals(VALIDATION_ERROR_EMAIL_IS_NOT_VALID, exception.getMessage());
    }

    @Test
    void deve_retornar_string_correta_no_toString() {
        Email email = new Email("usuario@email.com");
        assertEquals("usuario@email.com", email.toString());
    }
}