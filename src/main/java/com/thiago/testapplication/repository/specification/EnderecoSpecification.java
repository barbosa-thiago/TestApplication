package com.thiago.testapplication.repository.specification;

import com.thiago.testapplication.model.Endereco;
import org.springframework.data.jpa.domain.Specification;

public class EnderecoSpecification {

    public static Specification<Endereco> enderecoPrincipal() {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("enderecoPrincipal"), true);
    }

    public static Specification<Endereco> pessoaId(Long pessoaId) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.join("pessoa").get("id"), pessoaId);
    }
}
