package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    void deve_criar_telefone_valido() {
        Phone phone = new Phone("11999999999");
        assertEquals("11999999999", phone.value());
    }

    @Test
    void deve_lancar_excecao_para_telefone_nulo() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Phone(null));
        assertEquals(VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void deve_lancar_excecao_para_telefone_em_branco() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Phone("   "));
        assertEquals(VALIDATION_ERROR_PHONE_NUMBER_CANNOT_BE_BLANK, exception.getMessage());
    }

    @Test
    void deve_retornar_string_correta_no_toString() {
        Phone phone = new Phone("11999999999");
        assertEquals("11999999999", phone.toString());
    }
}