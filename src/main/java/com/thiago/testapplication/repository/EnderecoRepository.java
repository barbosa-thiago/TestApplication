package com.thiago.testapplication.repository;

import com.thiago.testapplication.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query(value = """
    select e from Endereco e
    join Pessoa p on p.id = e.pessoa.id
    where p.id = :id""")
    List<Endereco> findByPessoaId(Long id);
}
