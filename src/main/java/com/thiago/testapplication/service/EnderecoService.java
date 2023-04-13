package com.thiago.testapplication.service;

import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
import com.thiago.testapplication.dto.endereco.EnderecoUpdateDTO;
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
    private final PessoaService pessoaService;

    /***
     * Registro de Endereços, necessita o ID de uma Pessoa salva previamente
     * @param body dados a serem salvos
     * @return Endereço salvo
     */
    public Endereco save(EnderecoSaveDTO body) {
        var pessoa = pessoaService.getByID(body.pessoaId());

        if (body.enderecoPrincipal()) {
            getByPessoaId(body.pessoaId())
                .forEach(endereco -> {
                    endereco.setEnderecoPrincipal(false);
                    enderecoRepository.save(endereco);
                });
        }
        var endereco = EnderecoMapper.INSTANCE.enderecoDTOToEndereco(body);
        endereco.setPessoa(pessoa);

        return enderecoRepository.save(endereco);
    }

    /***
     * Update de Endereço
     * @param body dados a serem registrados
     * @return {@link Endereco} salva
     */
    public Endereco update(Long id, EnderecoUpdateDTO body) {
        var endereco = getByID(id);

        if (body.enderecoPrincipal())
            getByPessoaId(endereco.getPessoa().getId())
                .forEach(end -> {
                    end.setEnderecoPrincipal(false);
                    enderecoRepository.save(end);
                });

        EnderecoMapper.INSTANCE.update(body, endereco);
        return enderecoRepository.save(endereco);
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

    /***
     * Remove registro do banco
     * @param id do Endereco a ser removido
     */
    public void delete(Long id) {
        var endereco = getByID(id);
        enderecoRepository.delete(endereco);
    }
}
