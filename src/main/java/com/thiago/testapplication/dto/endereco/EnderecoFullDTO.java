package com.thiago.testapplication.dto.endereco;

public record EnderecoFullDTO(
    Long id,
    String logradouro,
    String cep,
    String numero,
    String cidade,
    Long pessoaId,
    Boolean enderecoPrincipal
) {
}
