package com.thiago.testapplication.common;

import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
import com.thiago.testapplication.dto.pessoa.PessoaSaveDTO;
import com.thiago.testapplication.dto.pessoa.PessoaUpdateDTO;
import com.thiago.testapplication.model.Endereco;
import com.thiago.testapplication.model.Pessoa;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class PessoaFakeFactory {
    public static PessoaSaveDTO createPessoaSaveDTO() {

        return new PessoaSaveDTO(
            "Thiago",
            LocalDate.of(1983, 4, 26)
        );
    }

    public static PessoaUpdateDTO createPessoaSaveDTOForUpdate() {

        return new PessoaUpdateDTO(
            "UpdatedName",
            LocalDate.of(1990, 4, 10));
    }

    public static Pessoa createPessoa() {

        return Pessoa.builder()
            .dataNascimento(LocalDate.of(1983, 4, 26))
            .nome("Thiago")
            .id(100L)
            .enderecos(Set.of(createEndereco()))
            .build();
    }

    public static Endereco createEndereco() {
        return new Endereco("rua Tomas Lopes",
            "60060160",
            "200",
            "Fortaleza",
            true, null);
    }

    public static Endereco createFullEndereco() {
        return new Endereco("rua Tomas Lopes",
            "60060160",
            "200",
            "Fortaleza",
            true, createPessoa());
    }

    public static EnderecoSaveDTO createEnderecoSaveDTO() {
        return new EnderecoSaveDTO("rua Tomas Lopes",
            "60060160",
            "200",
            "Fortaleza",
            true,
            null);
    }

    public static EnderecoSaveDTO createFullEnderecoSaveDTO() {
        return new EnderecoSaveDTO("rua Tomas Lopes",
            "60060160",
            "200",
            "Fortaleza",
            true,
            100L);
    }
}
