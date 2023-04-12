package com.thiago.testapplication.mapper;

import com.thiago.testapplication.dto.endereco.EnderecoDTO;
import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
import com.thiago.testapplication.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    @Mapping(target = "pessoaId", source = "pessoa.id")
    EnderecoDTO enderecoDTOToEndereco(Endereco endereco);

    @Mapping(target = "pessoa.id", source = "pessoaId")
    Endereco enderecoDTOToEndereco(EnderecoSaveDTO enderecoSaveDTO);

    @IterableMapping(elementTargetType = EnderecoDTO.class)
    List<EnderecoDTO> enderecosDTOToEnderecos(List<Endereco> enderecos);
}
