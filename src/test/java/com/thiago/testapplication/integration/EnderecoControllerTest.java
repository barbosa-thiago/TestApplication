package com.thiago.testapplication.integration;

import com.thiago.testapplication.common.PessoaFakeFactory;
import com.thiago.testapplication.dto.endereco.EnderecoFullDTO;
import com.thiago.testapplication.dto.pessoa.PessoaDTO;
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
            EnderecoFullDTO.class);

        Assertions.assertThat(enderecoDTOResponseEntity.getBody()).hasNoNullFieldsOrProperties();
    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void saveEndereco_ThrowsException_WhenPessoaIdNotPassed() {
        var enderecoSaveDTO = PessoaFakeFactory.createEnderecoSaveDTO();

        var enderecoDTOResponseEntity = restTemplate.exchange("/enderecos",
            HttpMethod.POST,
            new HttpEntity<>(enderecoSaveDTO),
            EnderecoFullDTO.class);

        Assertions.assertThat(enderecoDTOResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Sql({"/scripts-h2/pessoa.sql", "/scripts-h2/endereco.sql"})
    void getEnderecoByPessoa_ReturnsEnderecoList_WhenSuccessful() {

        var enderecoDTOResponseEntity = restTemplate.exchange("/enderecos/{pessoaId}",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<EnderecoFullDTO>>() {
            },100L);

        Assertions.assertThat(enderecoDTOResponseEntity.getBody()).isNotEmpty();
        Assertions.assertThat(enderecoDTOResponseEntity.getBody().get(0)).hasNoNullFieldsOrProperties();

    }

    @Test
    @Sql({"/scripts-h2/pessoa.sql", "/scripts-h2/endereco.sql"})
    void update_ReplacesEndereco_WhenSuccessful() {
        var enderecoUpdateDTO = PessoaFakeFactory.createEnderecoUpdateDTO();


        var endereco = restTemplate.exchange("/enderecos/{id}",
            HttpMethod.PUT,
            new HttpEntity<>(enderecoUpdateDTO),
            EnderecoFullDTO.class,
            100);

        Assertions.assertThat(endereco.getBody()).hasNoNullFieldsOrProperties();
        Assertions.assertThat(endereco.getBody().cidade()).isEqualTo("Nova cidade");

    }


    @Test
    @Sql({"/scripts-h2/pessoa.sql", "/scripts-h2/endereco.sql"})
    void delete_RemovesEndereco_WhenSuccessful() {


        var pessoaDTO = restTemplate.exchange("/enderecos/{id}",
            HttpMethod.DELETE,
            null,
            Void.class,
            100);

        Assertions.assertThat(pessoaDTO.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}