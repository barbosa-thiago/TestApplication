package com.thiago.testapplication.repository;

import com.thiago.testapplication.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}