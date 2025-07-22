package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_CANNOT_BE_BLANK;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_DOCUMENT_CANNOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    @Test
    void deve_criar_documento_valido() {
        Document document = new Document("12345678901");
        assertEquals("12345678901", document.value());
    }

    @Test
    void deve_lancar_excecao_para_documento_nulo() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Document(null));
        assertEquals(VALIDATION_ERROR_DOCUMENT_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void deve_lancar_excecao_para_documento_em_branco() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Document("   "));
        assertEquals(VALIDATION_ERROR_DOCUMENT_CANNOT_BE_BLANK, exception.getMessage());
    }

    @Test
    void deve_retornar_string_correta_no_toString() {
        Document document = new Document("12345678901");
        assertEquals("12345678901", document.toString());
    }
}