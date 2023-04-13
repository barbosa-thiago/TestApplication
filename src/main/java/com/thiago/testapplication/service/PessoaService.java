package com.thiago.testapplication.service;

import com.thiago.testapplication.dto.pessoa.PessoaSaveDTO;
import com.thiago.testapplication.dto.pessoa.PessoaUpdateDTO;
import com.thiago.testapplication.mapper.PessoaMapper;
import com.thiago.testapplication.model.Pessoa;
import com.thiago.testapplication.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    /***
     * Registro de pessoa
     * @param body dados a serem registrados
     * @return {@link Pessoa} salva
     */
    public Pessoa save(PessoaSaveDTO body) {

        if (body.dataNascimento().isAfter(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento inválida");

        var pessoa = PessoaMapper.INSTANCE.pessoaSaveDTOToPessoa(body);

        return pessoaRepository.save(pessoa);
    }

    /***
     * Busca Pessoa por ID
     * @param id a ser pesquisado
     * @return {@link Pessoa} encontrada
     */
    public Pessoa getByID(Long id) {
        return pessoaRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa não encontrada"));
    }

    /***
     * Update de pessoa
     * @param body dados a serem registrados
     * @return {@link Pessoa} salva
     */
    public Pessoa update(Long id, PessoaUpdateDTO body) {
        var pessoa = getByID(id);

        if (body.dataNascimento().isAfter(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de nascimento inválida");

        PessoaMapper.INSTANCE.update(body, pessoa);
        return pessoaRepository.save(pessoa);
    }

    /***
     * Listagem de Pessoas
     * @param pageable
     * @return Lista paginada de Pessoas
     */
    public Page<Pessoa> getAll(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    /***
     * Remove registro do banco
     * @param id da Pessoa a ser removido
     */
    public void delete(Long id) {
        var pessoa = getByID(id);
        pessoaRepository.delete(pessoa);
    }


}
