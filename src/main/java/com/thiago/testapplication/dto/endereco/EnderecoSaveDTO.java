package com.thiago.testapplication.dto.endereco;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EnderecoSaveDTO(
    @NotEmpty(message = "Logradouro não pode ser nulo")
    String logradouro,
    @NotEmpty(message = "CEP não pode ser nulo")
    String cep,
    @NotEmpty(message = "Número não pode ser nulo")
    String numero,
    @NotEmpty(message = "Cidade não pode ser nulo")
    String cidade,
    @NotNull(message = "Endereço Princial não pode ser nulo")
    Boolean enderecoPrincipal,
    @NotNull(message = "Id da Pessoa não pode ser nulo")
    Long pessoaId
) {
}
