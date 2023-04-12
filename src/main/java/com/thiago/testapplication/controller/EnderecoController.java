package com.thiago.testapplication.controller;

import com.thiago.testapplication.dto.endereco.EnderecoDTO;
import com.thiago.testapplication.dto.endereco.EnderecoSaveDTO;
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
    public ResponseEntity<EnderecoDTO> save(@RequestBody @Valid EnderecoSaveDTO body) {
        var endereco = enderecoService.save(body);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.enderecoDTOToEndereco(endereco));
    }

    @GetMapping("/{pessoaId}")
    public ResponseEntity<List<EnderecoDTO>> getAllByPessoa(@PathVariable Long pessoaId) {
        var enderecos = enderecoService.getByPessoaId(pessoaId);

        return ResponseEntity.ok(mapper.enderecosDTOToEnderecos(enderecos));
    }
}
