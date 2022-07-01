package br.org.pti.piracema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.pti.piracema.entities.Antena;
import br.org.pti.piracema.repositories.AntenaRepository;
import br.org.pti.piracema.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class AntenaService {

    private final AntenaRepository antenaRepository;

    public AntenaService(AntenaRepository antenaRepository) {
        this.antenaRepository = antenaRepository;
    }

    public Antena save(Antena antena) {
        return antenaRepository.save(antena);
    }

    public Antena findById(Long id) {
        Optional<Antena> antena = antenaRepository.findById(id);
        return antena.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Antena update(Antena antena) {
        return antenaRepository.save(antena);
    }
    
    public List<Antena> findAllByOrderByIdAsc() {
        List<Antena> antenas = antenaRepository.findAllByOrderByIdAsc();
        if (antenas.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return antenas;
        }
    }

}
