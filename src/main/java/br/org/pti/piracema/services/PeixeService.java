package br.org.pti.piracema.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.pti.piracema.entities.Peixe;
import br.org.pti.piracema.entities.dtos.PeixeFilterListDTO;
import br.org.pti.piracema.repositories.PeixeRepository;
import br.org.pti.piracema.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class PeixeService {

    private final PeixeRepository peixeRepository;

    public PeixeService(PeixeRepository peixeRepository) {
        this.peixeRepository = peixeRepository;
    }

    public Peixe save(Peixe peixe) {
        if (peixe.getRecaptura() == null) {
            peixe.setRecaptura(false);
        }
        if (existsByPitTag(peixe.getPitTag())) {
            throw new Error("PitTag is already in use");
        }
        return peixeRepository.save(peixe);
    }

    public Peixe update(Peixe peixe) {
        return peixeRepository.save(peixe);
    }

    public Peixe saveRecaptura(Peixe peixe) {
        if (peixe.getRecaptura() == null) {
            peixe.setRecaptura(false);
        }
        return peixeRepository.save(peixe);
    }

    public Boolean existsByPitTag(String pitTag) {
        return peixeRepository.existsByPitTag(pitTag);
    }

    // public Peixe findById(Long id) {
    // Optional<Peixe> peixe = peixeRepository.findById(id);
    // if (peixe.isEmpty()) {
    // throw new NoSuchElementException();
    // }
    // return peixe.get();
    // }

    public Peixe findById(Long id) {
        Optional<Peixe> optionalPeixe = peixeRepository.findById(id);
        return optionalPeixe.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    // ----------------------------------------------
    // public Peixe findByPitTag(String pitTag) {
    // Optional<Peixe> peixe = peixeRepository.findByInativoFalseAndPitTag(pitTag);
    // if (peixe.isEmpty()) {
    // throw new NoSuchElementException();
    // }
    // return peixe.get();
    // }

    public List<Peixe> findAllByPitTag(String pitTag) {
        return peixeRepository.findAllByPitTag(pitTag);
    }

    public Peixe findByInativoFalseAndPitTag(String pitTag) {
        Optional<Peixe> peixe = peixeRepository.findByInativoFalseAndPitTag(pitTag);
        return peixe.orElseThrow(() -> new ResourceNotFoundException(pitTag));
    }

    // ----------------------------------------------

    // public List<Peixe> findAllByInativoFalseOrderByIdAsc(int pageNumber, int
    // pageSize) {
    // if (pageSize >= 10)
    // pageSize = 10;
    // Pageable page = PageRequest.of(pageNumber, pageSize);
    // List<Peixe> peixes = peixeRepository.findAllByInativoFalseOrderByIdAsc(page);
    // if (peixes.isEmpty()) {
    // throw new ResourceNotFoundException();
    // } else {
    // return peixes;
    // }
    // }

    public List<Peixe> findAllByInativoFalseOrderByIdAsc() {
        List<Peixe> peixes = peixeRepository.findAllByInativoFalseOrderByIdAsc();
        if (peixes.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return peixes;
        }
    }

    public List<Peixe> findByInativoFalseAndLocalCaptura(String localCaptura, int pageNumber, int pageSize) {
        if (pageSize >= 10)
            pageSize = 10;
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Peixe> peixes = peixeRepository.findByInativoFalseAndLocalCapturaOrderByIdAsc(localCaptura, page);
        if (peixes.isEmpty()) {
            throw new ResourceNotFoundException(localCaptura);
        } else {
            return peixes;
        }
    }

    public List<Peixe> findByInativoFalseAndDataSolturaBetween(LocalDate dataInicio, LocalDate dataFim,
            int pageNumber, int pageSize) {
        if (pageSize >= 10)
            pageSize = 10;
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Peixe> peixes = peixeRepository.findByInativoFalseAndDataSolturaBetweenOrderByIdAsc(dataInicio, dataFim,
                page);
        if (peixes.isEmpty()) {
            throw new ResourceNotFoundException(dataInicio);
        } else {
            return peixes;
        }
    }

    public List<Peixe> findByInativoFalseAndLocalSoltura(String localSoltura, int pageNumber, int pageSize) {
        if (pageSize >= 10)
            pageSize = 10;
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Peixe> peixes = peixeRepository.findByInativoFalseAndLocalSolturaOrderByIdAsc(localSoltura, page);
        if (peixes.isEmpty()) {
            throw new ResourceNotFoundException(localSoltura);
        } else {
            return peixes;
        }
    }

    public Peixe findByInativoFalseAndAmostraDnaOrderByIdAsc(String amostraDna) {
        Optional<Peixe> peixe = peixeRepository.findByInativoFalseAndAmostraDnaOrderByIdAsc(amostraDna);
        return peixe.orElseThrow(() -> new ResourceNotFoundException(amostraDna));
    }

    // public List<Peixe> findByInativoFalseAndRecaptura(Boolean recaptura, int
    // pageNumber, int pageSize) {
    // if(pageSize >= 10) {pageSize = 10;}
    // Pageable page = PageRequest.of(pageNumber, pageSize);
    // return peixeRepository.findByInativoFalseAndRecapturaOrderByIdAsc(recaptura,
    // page);
    // }

    public List<Peixe> findByInativoFalseAndRecaptura(Boolean recaptura) {
        List<Peixe> peixes = peixeRepository.findByInativoFalseAndRecapturaOrderByIdAsc(recaptura);
        if (peixes.isEmpty()) {
            throw new ResourceNotFoundException(recaptura);
        } else {
            return peixes;
        }
    }

    public List<Peixe> findAllNomeCientificoByInativoFalseOrderByIdAsc() {
        return peixeRepository.findAllNomeCientificoByInativoFalseOrderByIdAsc();
    }

    public List<Peixe> findByInativoFalseAndNomeCientifico(String nomeCientifico, int pageNumber, int pageSize) {
        if (pageSize >= 10)
            pageSize = 10;
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Peixe> peixes = peixeRepository.findByInativoFalseAndNomeCientificoOrderByIdAsc(nomeCientifico, page);
        if (peixes.isEmpty()) {
            throw new ResourceNotFoundException(nomeCientifico);
        } else {
            return peixes;
        }
    }

    public List<String> findAllNomeCientifico(List<PeixeFilterListDTO> peixeFilterListDTO) {
        List<String> nomeCientifico = new ArrayList<>();
        for (int i = 0; i < peixeFilterListDTO.size(); i++) {
            nomeCientifico.add(peixeFilterListDTO.get(i).getNomeCientifico());
        }
        return nomeCientifico.stream().distinct().collect(Collectors.toList());
    }

}
