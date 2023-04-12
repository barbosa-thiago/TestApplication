package com.thiago.testapplication.dto.endereco;

public record EnderecoDTO(
    Long id,
    String logradouro,
    String cep,
    String numero,
    String cidade,
    Long pessoaId,
    Boolean enderecoPrincipal
) {
}
