package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_IN_THE_FUTURE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BirthDateTest {

    @Test
    void deve_criar_com_data_valida() {
        LocalDate data = LocalDate.of(2000, 1, 1);
        BirthDate birthDate = new BirthDate(data);
        assertThat(birthDate.value()).isEqualTo(data);
    }

    @Test
    void deve_lancar_excecao_para_data_nula() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new BirthDate(null))
                .withMessage(VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_NULL);
    }

    @Test
    void deve_lancar_excecao_para_data_no_futuro() {
        LocalDate futura = LocalDate.now().plusDays(1);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new BirthDate(futura))
                .withMessage(VALIDATION_ERROR_BIRTH_DATE_CANNOT_BE_IN_THE_FUTURE);
    }

    @Test
    void deve_calcular_idade_corretamente() {
        LocalDate nascimento = LocalDate.of(LocalDate.now().getYear() - 20, 1, 1);
        BirthDate birthDate = new BirthDate(nascimento);
        assertThat(birthDate.age()).isEqualTo(20);
    }

}
