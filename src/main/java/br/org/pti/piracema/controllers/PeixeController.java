package br.org.pti.piracema.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.pti.piracema.entities.Peixe;
import br.org.pti.piracema.entities.dtos.PeixeBlockRegistrationDTO;
import br.org.pti.piracema.entities.dtos.PeixeDTO;
import br.org.pti.piracema.entities.dtos.PeixeFilterListDTO;
import br.org.pti.piracema.entities.dtos.PeixeFormDTO;
import br.org.pti.piracema.entities.dtos.PeixeUpdateDTO;
import br.org.pti.piracema.services.PeixeService;
import br.org.pti.piracema.services.exceptions.DataBaseException;
import br.org.pti.piracema.services.exceptions.DataIntegrationViolation;

@RestController
@RequestMapping("/peixe")
public class PeixeController {

    private final PeixeService peixeService;
    private final ModelMapper modelMapper;

    public PeixeController(PeixeService peixeService, ModelMapper modelMapper) {
        this.peixeService = peixeService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Peixe peixe) {
        try {
            return ResponseEntity.ok(modelMapper.map(peixeService.save(peixe), PeixeDTO.class));
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrationViolation(e.getMessage());
        }
    }

    @PostMapping("/bloco")
    public ResponseEntity<?> createBlock(@RequestBody PeixeBlockRegistrationDTO peixeBlockRegistrationDTO) {
        try {
            List<Peixe> listPeixe = new ArrayList<>();
            for (int i = 0; i < peixeBlockRegistrationDTO.getUnico().size(); i++) {
                Peixe peixe = new Peixe();
                modelMapper.map(peixeBlockRegistrationDTO.getRepetivel().get(0), peixe);
                modelMapper.map(peixeBlockRegistrationDTO.getUnico().get(i), peixe);
                listPeixe.add(peixe);
            }
            for (int i = 0; i < listPeixe.size(); i++) {
                peixeService.save(listPeixe.get(i));
            }
            return ResponseEntity.status(HttpStatus.OK).body(listPeixe);
        } catch (Error e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrationViolation(e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(
            @RequestParam(value = "recaptura", required = false) Boolean recaptura,
            @RequestParam(value = "pittag", required = false) String value,
            @RequestBody PeixeUpdateDTO peixeUpdateDTO) {
        try {
            List<Peixe> peixeAll = new ArrayList<>();
            Peixe peixeOld = peixeService.findByInativoFalseAndPitTag(value);
            Peixe peixeNew = new Peixe();
            peixeAll = peixeService.findAllByPitTag(peixeOld.getPitTag());
            modelMapper.map(peixeOld, peixeNew);
            peixeNew.setId(null);
            modelMapper.map(peixeUpdateDTO, peixeNew);

            for (int i = 0; i < peixeAll.size(); i++) {
                peixeAll.get(i).setNomeCientifico(peixeNew.getNomeCientifico());
                peixeAll.get(i).setComprimentoPadrao(peixeNew.getComprimentoPadrao());
            }

            if (recaptura = true) {
                peixeNew.setRecaptura(true);
            }

            peixeOld.setInativo(true);
            peixeService.update(peixeOld);
            peixeService.update(peixeNew);

            return ResponseEntity.ok(modelMapper.map(peixeNew, PeixeDTO.class));

        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    @PutMapping("/delete")
    public ResponseEntity<?> delete(
            @RequestParam(value = "pittag", required = false) String value) {
        try {
            Peixe peixe = peixeService.findByInativoFalseAndPitTag(value);
            peixe.setInativo(true);
            peixeService.update(peixe);
            return ResponseEntity.ok(modelMapper.map(peixe, PeixeFilterListDTO.class));
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    // @GetMapping("/all/{pageNumber}/{pageSize}")
    // public ResponseEntity<List<PeixeFilterListDTO>> findAll(@PathVariable int
    // pageNumber, @PathVariable int pageSize) {
    // List<Peixe> peixe =
    // peixeService.findAllByInativoFalseOrderByIdAsc(pageNumber, pageSize);
    // List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
    // .map(p -> modelMapper.map(p,
    // PeixeFilterListDTO.class)).collect(Collectors.toList());
    // return ResponseEntity.ok(peixeFilterListDTO);
    // }

    @GetMapping("/all")
    public ResponseEntity<List<PeixeFilterListDTO>> findAll() {
        List<Peixe> peixe = peixeService.findAllByInativoFalseOrderByIdAsc();
        List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
                .map(p -> modelMapper.map(p, PeixeFilterListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    @GetMapping("/allnomecientifico")
    public ResponseEntity<List<String>> findAllNomeCientifico() {
        List<Peixe> peixes = peixeService.findAllNomeCientificoByInativoFalseOrderByIdAsc();
        List<PeixeFilterListDTO> peixesDTO = peixes.stream().map(p -> modelMapper.map(p, PeixeFilterListDTO.class))
                .collect(Collectors.toList());
        List<String> especies = peixeService.findAllNomeCientifico(peixesDTO);
        return ResponseEntity.ok(especies);
    }

    @GetMapping("/pittag")
    public ResponseEntity<PeixeFilterListDTO> findByInativoFalseAndPitTag(@RequestParam("value") String value) {
        Peixe peixe = peixeService.findByInativoFalseAndPitTag(value);
        PeixeFilterListDTO peixeFilterListDTO = modelMapper.map(peixe, PeixeFilterListDTO.class);
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    @GetMapping("/nomecientifico/{pageNumber}/{pageSize}")
    public ResponseEntity<List<PeixeFilterListDTO>> findByInativoFalseAndNomeCientifico(
            @RequestParam("value") String value,
            @PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Peixe> peixe = peixeService.findByInativoFalseAndNomeCientifico(value, pageNumber, pageSize);
        List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
                .map(p -> modelMapper.map(p, PeixeFilterListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    @GetMapping("/localcaptura/{pageNumber}/{pageSize}")
    public ResponseEntity<List<PeixeFilterListDTO>> findByInativoFalseAndLocalCaptura(
            @RequestParam("value") String value,
            @PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Peixe> peixe = peixeService.findByInativoFalseAndLocalCaptura(value, pageNumber, pageSize);
        List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
                .map(p -> modelMapper.map(p, PeixeFilterListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    @GetMapping("/datasoltura/{pageNumber}/{pageSize}")
    public ResponseEntity<List<PeixeFilterListDTO>> findByInativoFalseAndDataSolturaBetween(
            @RequestBody PeixeFormDTO value,
            @PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Peixe> peixe = peixeService.findByInativoFalseAndDataSolturaBetween(value.getDataInicioConsulta(),
                value.getDataFimConsulta(), pageNumber, pageSize);
        List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
                .map(p -> modelMapper.map(p, PeixeFilterListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    @GetMapping("/localsoltura/{pageNumber}/{pageSize}")
    public ResponseEntity<List<PeixeFilterListDTO>> findByInativoFalseAndLocalSoltura(
            @RequestParam("value") String value,
            @PathVariable int pageNumber, @PathVariable int pageSize) {
        List<Peixe> peixe = peixeService.findByInativoFalseAndLocalSoltura(value, pageNumber, pageSize);
        List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
                .map(p -> modelMapper.map(p, PeixeFilterListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    @GetMapping("/amostradna")
    public ResponseEntity<PeixeFilterListDTO> findByInativoFalseAndAmostraDna(@RequestBody PeixeFormDTO value) {
        Peixe peixe = peixeService.findByInativoFalseAndAmostraDnaOrderByIdAsc(value.getAmostraDna());
        PeixeFilterListDTO peixeFilterListDTO = modelMapper.map(peixe, PeixeFilterListDTO.class);
        return ResponseEntity.ok(peixeFilterListDTO);
    }

    // @GetMapping("/recaptura")
    // public ResponseEntity<?>
    // findByInativoFalseAndRecaptura(@RequestParam("value") Boolean value,
    // @PathVariable int pageNumber, @PathVariable int pageSize) {
    // try {
    // List<Peixe> peixe = peixeService.findByInativoFalseAndRecaptura(value,
    // pageNumber, pageSize);
    // List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
    // .map(p -> modelMapper.map(p,
    // PeixeFilterListDTO.class)).collect(Collectors.toList());
    // return ResponseEntity.ok(peixeFilterListDTO);
    // } catch (Exception e) {
    // return ResponseEntity.notFound().build();
    // }
    // }

    @GetMapping("/recaptura")
    public ResponseEntity<?> findByInativoFalseAndRecaptura(@RequestParam("value") Boolean value) {
        List<Peixe> peixe = peixeService.findByInativoFalseAndRecaptura(value);
        List<PeixeFilterListDTO> peixeFilterListDTO = peixe.stream()
                .map(p -> modelMapper.map(p, PeixeFilterListDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(peixeFilterListDTO);
    }
}