package com.thiago.testapplication.service;

import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
import com.thiago.testapplication.mapper.EnderecoMapper;
import com.thiago.testapplication.model.Endereco;
import com.thiago.testapplication.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    /***
     * Registro de Endereços, necessita o ID de uma Pessoa salva previamente
     * @param body dados a serem salvos
     * @return Endereço salvo
     */
    public Endereco save(EnderecoSaveDTO body) {


        if (body.enderecoPrincipal()) {
            getByPessoaId(body.pessoaId())
                .forEach(endereco -> {
                    endereco.setEnderecoPrincipal(false);
                    enderecoRepository.save(endereco);
                });
        }
        var endereco = EnderecoMapper.INSTANCE.enderecoDTOToEndereco(body);

        return enderecoRepository.save(endereco);
    }

    /***
     * Update de Endereço
     * @param body dados a serem registrados
     * @return {@link Endereco} salva
     */
    public Endereco update(Long id, EnderecoSaveDTO body) {
        getByID(id);

        if (body.enderecoPrincipal())
            getByPessoaId(body.pessoaId())
                .forEach(endereco -> {
                    endereco.setEnderecoPrincipal(false);
                    enderecoRepository.save(endereco);
                });

        var pessoa = EnderecoMapper.INSTANCE.enderecoDTOToEndereco(body);
        return enderecoRepository.save(pessoa);
    }

    /***
     * Busca Endereço por ID
     * @param id a ser pesquisado
     * @return {@link Endereco} encontrada
     */
    public Endereco getByID(Long id) {
        return enderecoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Endereço não encontrado"));
    }

    /***
     * Busca Endereço pelo ID da Pessoa que o possui
     * @param id da pessoa para consulta
     * @return Lista de {@link Endereco}
     */
    public List<Endereco> getByPessoaId(Long id) {
        return enderecoRepository.findByPessoaId(id);
    }
}
