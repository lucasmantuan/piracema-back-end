package br.org.pti.piracema.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.pti.piracema.entities.Antena;
import br.org.pti.piracema.entities.dtos.AntenaDTO;
import br.org.pti.piracema.entities.dtos.AntenaNoPassagemAndStatusAntenaDTO;
import br.org.pti.piracema.entities.dtos.AntenaNoPassagemWithStatusAntenaDTO;
import br.org.pti.piracema.services.AntenaService;
import br.org.pti.piracema.services.exceptions.DataIntegrationViolation;

@RestController
@RequestMapping("/antena")
public class AntenaController {

    private final AntenaService antenaService;
    private final ModelMapper modelMapper;

    public AntenaController(AntenaService antenaService, ModelMapper modelMapper) {
        this.antenaService = antenaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Antena antena) {
        try {
            return ResponseEntity.ok(modelMapper.map(antenaService.save(antena), AntenaDTO.class));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrationViolation(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam(value = "id", required = false) Long id,
            @RequestBody Antena antena) {
        try {
            Antena antenaOld = antenaService.findById(id);
            modelMapper.map(antena, antenaOld);
            antenaService.update(antenaOld);
            return ResponseEntity.ok(modelMapper.map(antenaOld, AntenaNoPassagemAndStatusAntenaDTO.class));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrationViolation(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<AntenaNoPassagemWithStatusAntenaDTO>> findAll() {
        List<Antena> antena = antenaService.findAllByOrderByIdAsc();
        List<AntenaNoPassagemWithStatusAntenaDTO> antenaNoPassagemWithStatusAntenaDTO = antena.stream()
                .map(a -> modelMapper.map(a, AntenaNoPassagemWithStatusAntenaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(antenaNoPassagemWithStatusAntenaDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AntenaNoPassagemWithStatusAntenaDTO> findById(@PathVariable Long id) {
        Antena antena = antenaService.findById(id);
        AntenaNoPassagemWithStatusAntenaDTO antenaNoPassagemWithStatusAntenaDTO = modelMapper.map(antena,
                AntenaNoPassagemWithStatusAntenaDTO.class);
        return ResponseEntity.ok(antenaNoPassagemWithStatusAntenaDTO);
    }

}
