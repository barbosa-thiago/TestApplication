package com.thiago.testapplication.controller;

import com.thiago.testapplication.dto.pessoa.PessoaDTO;
import com.thiago.testapplication.dto.pessoa.PessoaSaveDTO;
import com.thiago.testapplication.dto.pessoa.PessoaUpdateDTO;
import com.thiago.testapplication.mapper.PessoaMapper;
import com.thiago.testapplication.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("pessoas")
@RestController
public class PessoaController {

    private final PessoaService pessoaService;
    private final PessoaMapper mapper;

    @PostMapping
    public ResponseEntity<PessoaDTO> save(@RequestBody @Valid PessoaSaveDTO body) {
        var pessoa = pessoaService.save(body);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapper.pessoaToPessoaDTO(pessoa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> getByID(@PathVariable Long id) {
        var pessoa = pessoaService.getByID(id);

        return ResponseEntity.ok(mapper.pessoaToPessoaDTO(pessoa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> update(@PathVariable Long id, @RequestBody @Valid PessoaUpdateDTO body) {
        var pessoa = pessoaService.update(id, body);

        return ResponseEntity.ok(mapper.pessoaToPessoaDTO(pessoa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pessoaService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping()
    public ResponseEntity<Page<PessoaDTO>> getAll(@ParameterObject Pageable pageable) {
        var pessoas = pessoaService.getAll(pageable);

        return ResponseEntity.ok(mapper.pessoasToPessoasDTO(pessoas));
    }
}
