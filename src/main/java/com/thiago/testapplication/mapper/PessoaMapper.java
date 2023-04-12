package com.thiago.testapplication.mapper;

import com.thiago.testapplication.dto.pessoa.PessoaDTO;
import com.thiago.testapplication.dto.pessoa.PessoaSaveDTO;
import com.thiago.testapplication.dto.pessoa.PessoaUpdateDTO;
import com.thiago.testapplication.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import static java.util.Objects.nonNull;

@Mapper(componentModel = "spring")
public interface PessoaMapper {
    PessoaMapper INSTANCE = Mappers.getMapper(PessoaMapper.class);

    Pessoa pessoaSaveDTOToPessoa(PessoaSaveDTO pessoaSaveDTO);

    Pessoa pessoaSaveDTOToPessoa(PessoaUpdateDTO pessoaSaveDTO);

    PessoaDTO pessoaToPessoaDTO(Pessoa pessoa);

    default Page<PessoaDTO> pessoasToPessoasDTO(Page<Pessoa> pessoas) {
        return pessoas.map(this::pessoaToPessoaDTO);
    }

    @AfterMapping
    default Pessoa afterPessoaMapping(@MappingTarget Pessoa.PessoaBuilder<?, ?> pessoaBuilder) {
        var pessoa = pessoaBuilder.build();
        if (nonNull(pessoa.getEnderecos())) {
            pessoa.getEnderecos().forEach(p -> p.setPessoa(pessoa));
        }
        return pessoa;
    }
}
