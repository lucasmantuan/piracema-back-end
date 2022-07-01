package br.org.pti.piracema.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.org.pti.piracema.entities.Passagem;
import br.org.pti.piracema.entities.dtos.PassagemDTO;
import br.org.pti.piracema.services.PassagemService;
import br.org.pti.piracema.services.exceptions.DataIntegrationViolation;

@RestController
@RequestMapping("/passagem")
public class PassagemController {

    private final PassagemService passagemService;
    private final ModelMapper modelMapper;

    public PassagemController(PassagemService passagemService, ModelMapper modelMapper) {
        this.passagemService = passagemService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<PassagemDTO>> create(@RequestParam("csvFile") MultipartFile csvFile) {
        try {
            List<Passagem> passagem = passagemService.upload(csvFile);
            List<PassagemDTO> passagemDTO = passagem.stream().map(p -> modelMapper.map(p, PassagemDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(passagemDTO);
        } catch (Exception e) {
            throw new DataIntegrationViolation(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Passagem> deleteById(@PathVariable Long id) {
        passagemService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // @GetMapping("/all/{pageNumber}/{pageSize}")
    // public ResponseEntity<List<PassagemDTO>> findAll(@PathVariable int
    // pageNumber, @PathVariable int pageSize) {
    // List<Passagem> passagem = passagemService.findAll(pageNumber, pageSize);
    // List<PassagemDTO> passagemDTO = passagem.stream().map(p -> modelMapper.map(p,
    // PassagemDTO.class))
    // .collect(Collectors.toList());
    // return ResponseEntity.ok(passagemDTO);
    // }

    @GetMapping("/all")
    public ResponseEntity<List<PassagemDTO>> findAll() {
        List<Passagem> passagem = passagemService.findAll();
        List<PassagemDTO> passagemDTO = passagem.stream().map(p -> modelMapper.map(p, PassagemDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(passagemDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassagemDTO> findById(@PathVariable Long id) {
        Passagem passagem = passagemService.findById(id);
        PassagemDTO passagemDTO = modelMapper.map(passagem, PassagemDTO.class);
        return ResponseEntity.ok(passagemDTO);
    }

}