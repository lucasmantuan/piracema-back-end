package br.org.pti.piracema.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.pti.piracema.entities.StatusAntena;
import br.org.pti.piracema.entities.dtos.StatusAntenaDTO;
import br.org.pti.piracema.entities.dtos.StatusAntenaFormDTO;
import br.org.pti.piracema.services.StatusAntenaService;
import br.org.pti.piracema.services.exceptions.DataIntegrationViolation;

@RestController
@RequestMapping("/statusantena")
public class StatusAntenaController {

    private final StatusAntenaService statusAntenaService;
    private final ModelMapper modelMapper;

    public StatusAntenaController(StatusAntenaService statusAntenaService, ModelMapper modelMapper) {
        this.statusAntenaService = statusAntenaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<StatusAntenaDTO> create(@RequestBody StatusAntenaFormDTO statusAntenaFormDTO) {
        try {
            StatusAntena statusAntena = statusAntenaService.save(statusAntenaFormDTO);
            StatusAntenaDTO statusAntenaDTO = modelMapper.map(statusAntena, StatusAntenaDTO.class);
            return ResponseEntity.ok(statusAntenaDTO);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrationViolation(e.getMessage());

        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<StatusAntenaDTO>> getAll() {
        List<StatusAntena> statusAntena = statusAntenaService.findAll();
        List<StatusAntenaDTO> statusAntenaDTO = statusAntena.stream()
                .map(a -> modelMapper.map(a, StatusAntenaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(statusAntenaDTO);
    }

}
