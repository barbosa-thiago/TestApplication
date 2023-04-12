package com.thiago.testapplication.integration;

import com.thiago.testapplication.common.PessoaFakeFactory;
import com.thiago.testapplication.dto.pessoa.PessoaDTO;
import com.thiago.testapplication.wrapper.PageableResponse;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PessoaControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void savePessoa_PersistsPessoa_WhenSuccessful() {
        var pessoaSaveDTO = PessoaFakeFactory.createPessoaSaveDTO();

        var pessoaDTO = restTemplate.exchange("/pessoas",
            HttpMethod.POST,
            new HttpEntity<>(pessoaSaveDTO),
            PessoaDTO.class);

        Assertions.assertThat(pessoaDTO.getBody()).hasNoNullFieldsOrProperties();

    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void getByID_ReturnsPessoaDTO_WhenSuccessful() {

        var pessoaDTO = restTemplate.exchange("/pessoas/{id}",
            HttpMethod.GET,
            null,
            PessoaDTO.class,
            100);

        Assertions.assertThat(pessoaDTO.getBody()).hasNoNullFieldsOrProperties();

    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void update_ReplacesPessoa_WhenSuccessful() {
        var pessoaUpdateDTO = PessoaFakeFactory.createPessoaSaveDTOForUpdate();


        var pessoaDTO = restTemplate.exchange("/pessoas/{id}",
            HttpMethod.PUT,
            new HttpEntity<>(pessoaUpdateDTO),
            PessoaDTO.class,
            100);

        Assertions.assertThat(pessoaDTO.getBody()).hasNoNullFieldsOrProperties();
        Assertions.assertThat(pessoaDTO.getBody().nome()).isEqualTo("UpdatedName");

    }

    @Test
    @Sql("/scripts-h2/pessoa.sql")
    void getAll_ReturnsPessoaDTO_WhenSuccessful() {

        var pessoaDTO = restTemplate.exchange("/pessoas",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<PageableResponse<PessoaDTO>>() {
            });

        Assertions.assertThat(pessoaDTO.getBody().getContent()).isNotEmpty();
        Assertions.assertThat(pessoaDTO.getBody().getContent().get(0)).hasNoNullFieldsOrProperties();

    }
}