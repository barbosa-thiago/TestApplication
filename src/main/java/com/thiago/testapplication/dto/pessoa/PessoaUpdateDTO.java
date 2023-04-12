package com.thiago.testapplication.dto.pessoa;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PessoaUpdateDTO(
    @NotEmpty(message = "Nome não pode ser nulo")
    String nome,
    @NotNull(message = "Data de nascimento não pode ser nulo")
    LocalDate dataNascimento
    ) {
}
