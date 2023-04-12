package com.thiago.testapplication.repository;

import com.thiago.testapplication.common.PessoaFakeFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class EnderecoRepositoryTest {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void save_PersistsEndereco_WhenSuccessful() {
        var endereco = enderecoRepository.save(PessoaFakeFactory.createFullEndereco());

        Assertions.assertThat(endereco).isNotNull();
        Assertions.assertThat(endereco).hasNoNullFieldsOrProperties();
    }

    @Test
    @Sql({"/scripts-h2/pessoa.sql", "/scripts-h2/endereco.sql"})
    void findById_ReturnsEndereco_WhenSuccessful() {
        var endereco = enderecoRepository.findById(100L);

        Assertions.assertThat(endereco).isNotEmpty();
        Assertions.assertThat(endereco.get()).hasNoNullFieldsOrProperties();
    }

    @Test
    @Sql({"/scripts-h2/pessoa.sql", "/scripts-h2/endereco.sql"})
    void findAll_ReturnsEnderecoPage_WhenSuccessful() {
        var enderecoPage = enderecoRepository.findAll(PageRequest.of(0, 10));

        Assertions.assertThat(enderecoPage).isNotEmpty();
        Assertions.assertThat(enderecoPage.getContent().get(0)).hasNoNullFieldsOrProperties();
    }
}
