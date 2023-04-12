package com.thiago.testapplication.repository;

import com.thiago.testapplication.common.PessoaFakeFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class PessoaRepositoryTest {

    @Autowired
    PessoaRepository pessoaRepository;

    @Test
    void save_PersistsPessoa_WhenSuccessful() {
        var pessoa = pessoaRepository.save(PessoaFakeFactory.createPessoa());

        Assertions.assertThat(pessoa).isNotNull();
        Assertions.assertThat(pessoa).hasNoNullFieldsOrProperties();
    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void findById_ReturnsPessoa_WhenSuccessful() {
        var pessoa = pessoaRepository.findById(100L);

        Assertions.assertThat(pessoa).isNotEmpty();
        Assertions.assertThat(pessoa.get()).hasNoNullFieldsOrProperties();
    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void findAll_ReturnsPessoaPage_WhenSuccessful() {
        var pessoaPage = pessoaRepository.findAll(PageRequest.of(0, 10));

        Assertions.assertThat(pessoaPage).isNotEmpty();
        Assertions.assertThat(pessoaPage.getContent().get(0)).hasNoNullFieldsOrProperties();
    }
}
