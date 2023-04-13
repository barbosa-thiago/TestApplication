package com.thiago.testapplication.mapper;

import com.thiago.testapplication.dto.endereco.EnderecoFullDTO;
import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
import com.thiago.testapplication.dto.endereco.EnderecoUpdateDTO;
import com.thiago.testapplication.model.Endereco;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    @Mapping(target = "pessoaId", source = "pessoa.id")
    EnderecoFullDTO enderecoDTOToEndereco(Endereco endereco);

    @Mapping(target = ".", source = "pessoaId", ignore = true)
    Endereco enderecoDTOToEndereco(EnderecoSaveDTO enderecoSaveDTO);

    void update(EnderecoUpdateDTO body, @MappingTarget Endereco endereco);

    @IterableMapping(elementTargetType = EnderecoFullDTO.class)
    List<EnderecoFullDTO> enderecosDTOToEnderecos(List<Endereco> enderecos);
}
