package com.thiago.testapplication.service;

import com.thiago.testapplication.common.PessoaFakeFactory;
import com.thiago.testapplication.model.Pessoa;
import com.thiago.testapplication.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PessoaServiceTest {

    @InjectMocks
    PessoaService pessoaService;

    @Mock
    PessoaRepository pessoaRepository;

    @BeforeEach
    void setup() {
        BDDMockito.when(pessoaRepository.save(ArgumentMatchers.any(Pessoa.class)))
            .thenReturn(PessoaFakeFactory.createPessoa());

        BDDMockito.when(pessoaRepository.findById(ArgumentMatchers.anyLong()))
            .thenReturn(Optional.of(PessoaFakeFactory.createPessoa()));
    }

    @Test
    void update_ReplacesPessoa_WhenSuccessful() {
        var pessoa = pessoaService.update(100L, PessoaFakeFactory.createPessoaSaveDTOForUpdate());

        Assertions.assertThat(pessoa).isNotNull();
        Assertions.assertThat(pessoa).hasNoNullFieldsOrProperties();
    }

    @Test
    void getByID_ReturnsPessoa_WhenSuccessful() {
        var pessoa = pessoaService.getByID(100L);

        Assertions.assertThat(pessoa).isNotNull();
        Assertions.assertThat(pessoa).hasNoNullFieldsOrProperties();
    }



}