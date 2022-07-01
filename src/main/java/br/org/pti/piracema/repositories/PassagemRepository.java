package br.org.pti.piracema.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.pti.piracema.entities.Passagem;

@Repository
public interface PassagemRepository extends JpaRepository<Passagem, Long> {

    List<Passagem> findAllByOrderByIdAsc();

    // List<Passagem> findAllByOrderByIdAsc(Pageable page);

}
