package com.thiago.testapplication.service;

import com.thiago.testapplication.common.PessoaFakeFactory;
import com.thiago.testapplication.model.Endereco;
import com.thiago.testapplication.repository.EnderecoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    EnderecoService enderecoService;

    @Mock
    EnderecoRepository enderecoRepository;
    @Mock
    PessoaService pessoaService;

    @BeforeEach
    void setup() {
        BDDMockito.when(enderecoRepository.save(ArgumentMatchers.any(Endereco.class)))
            .thenReturn(PessoaFakeFactory.createFullEndereco());

        BDDMockito.when(enderecoRepository.findByPessoaId(ArgumentMatchers.anyLong()))
            .thenReturn(List.of(PessoaFakeFactory.createFullEndereco()));
    }

    @Test
    void save_PersistsEndereco_WhenSuccessful() {
        var endereco = enderecoService.save(PessoaFakeFactory.createFullEnderecoSaveDTO());

        Assertions.assertThat(endereco).isNotNull();
        Assertions.assertThat(endereco).hasNoNullFieldsOrPropertiesExcept("id");
    }

    @Test
    void getByPessoaID_ReturnsEndereco_WhenSuccessful() {
        var endereco = enderecoService.getByPessoaId(100L);

        Assertions.assertThat(endereco).isNotEmpty();
        Assertions.assertThat(endereco.get(0)).hasNoNullFieldsOrPropertiesExcept("id");
    }
}