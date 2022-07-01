package br.org.pti.piracema.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.pti.piracema.entities.Antena;
import br.org.pti.piracema.entities.StatusAntena;
import br.org.pti.piracema.entities.dtos.StatusAntenaFormDTO;
import br.org.pti.piracema.repositories.StatusAntenaRepository;
import br.org.pti.piracema.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class StatusAntenaService {

    private final StatusAntenaRepository statusAntenaRepository;
    private final AntenaService antenaService;

    public StatusAntenaService(StatusAntenaRepository statusAntenaRepository, AntenaService antenaService) {
        this.statusAntenaRepository = statusAntenaRepository;
        this.antenaService = antenaService;
    }

    public StatusAntena save(StatusAntenaFormDTO statusAntenaDTO) {
        Antena antena = antenaService.findById(statusAntenaDTO.getAntenaId());
        StatusAntena statusAntena = new StatusAntena();
        statusAntena.setStatus(statusAntenaDTO.getStatus());
        statusAntena.setDataMudancaStatus(statusAntenaDTO.getDataMudancaStatus());
        antena.getStatusAntena().add(statusAntena);
        statusAntena.setAntena(antena);
        return statusAntenaRepository.save(statusAntena);
    }

    public List<StatusAntena> findAll() {
        List<StatusAntena> statusAntenas = statusAntenaRepository.findAll();
        if (statusAntenas.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return statusAntenas;
        }
    }

    public StatusAntena findByLastId(Long id) {
        return null;
    }

}