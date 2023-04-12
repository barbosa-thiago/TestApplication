package com.thiago.testapplication.integration;

import com.thiago.testapplication.common.PessoaFakeFactory;
import com.thiago.testapplication.dto.endereco.EnderecoDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class EnderecoControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void saveEndereco_PersistsEndereco_WhenSuccessful() {
        var enderecoSaveDTO = PessoaFakeFactory.createFullEnderecoSaveDTO();

        var enderecoDTOResponseEntity = restTemplate.exchange("/enderecos",
            HttpMethod.POST,
            new HttpEntity<>(enderecoSaveDTO),
            EnderecoDTO.class);

        Assertions.assertThat(enderecoDTOResponseEntity.getBody()).hasNoNullFieldsOrProperties();
    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void saveEndereco_ThrowsException_WhenPessoaIdNotPassed() {
        var enderecoSaveDTO = PessoaFakeFactory.createEnderecoSaveDTO();

        var enderecoDTOResponseEntity = restTemplate.exchange("/enderecos",
            HttpMethod.POST,
            new HttpEntity<>(enderecoSaveDTO),
            EnderecoDTO.class);

        Assertions.assertThat(enderecoDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Sql({"/scripts-h2/pessoa.sql", "/scripts-h2/endereco.sql"})
    void getEnderecoByPessoa_ReturnsEnderecoList_WhenSuccessful() {

        var enderecoDTOResponseEntity = restTemplate.exchange("/enderecos/{pessoaId}",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<EnderecoDTO>>() {
            },100L);

        Assertions.assertThat(enderecoDTOResponseEntity.getBody()).isNotEmpty();
        Assertions.assertThat(enderecoDTOResponseEntity.getBody().get(0)).hasNoNullFieldsOrProperties();

    }

}