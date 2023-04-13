package com.thiago.testapplication.controller;

import com.thiago.testapplication.dto.endereco.EnderecoFullDTO;
import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
import com.thiago.testapplication.dto.endereco.EnderecoUpdateDTO;
import com.thiago.testapplication.mapper.EnderecoMapper;
import com.thiago.testapplication.service.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;
    private final EnderecoMapper mapper;

    @PostMapping
    public ResponseEntity<EnderecoFullDTO> save(@RequestBody @Valid EnderecoSaveDTO body) {
        var endereco = enderecoService.save(body);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.enderecoDTOToEndereco(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoFullDTO> update(@PathVariable Long id,
                                                  @RequestBody @Valid EnderecoUpdateDTO body) {
        var endereco = enderecoService.update(id, body);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.enderecoDTOToEndereco(endereco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<List<EnderecoFullDTO>> getAllByPessoa(@PathVariable Long pessoaId) {
        var enderecos = enderecoService.getByPessoaId(pessoaId);

        return ResponseEntity.ok(mapper.enderecosDTOToEnderecos(enderecos));
    }
}
