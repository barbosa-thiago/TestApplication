package com.thiago.testapplication.dto.pessoa;


import com.thiago.testapplication.dto.endereco.EnderecoDTO;

import java.time.LocalDate;
import java.util.List;

public record PessoaDTO(
    Long id, String nome, LocalDate dataNascimento, List<EnderecoDTO> enderecos
    ) {
}
