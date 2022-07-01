package br.org.pti.piracema.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.pti.piracema.entities.Antena;

@Repository
public interface AntenaRepository extends JpaRepository<Antena, Long> {

    List<Antena> findAllByOrderByIdAsc();

}
