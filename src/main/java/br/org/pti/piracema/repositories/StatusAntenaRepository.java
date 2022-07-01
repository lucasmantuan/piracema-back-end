package br.org.pti.piracema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.pti.piracema.entities.StatusAntena;

@Repository
public interface StatusAntenaRepository extends JpaRepository<StatusAntena, Long> {

}
