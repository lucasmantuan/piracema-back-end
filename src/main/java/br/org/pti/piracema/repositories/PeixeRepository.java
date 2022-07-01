package br.org.pti.piracema.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.pti.piracema.entities.Peixe;

@Repository
public interface PeixeRepository extends JpaRepository<Peixe, Long> {

    Boolean existsByPitTag(String pitTag);

    // ----------------------------------------------
    // Optional<Peixe> findByInativoFalseAndPitTag(String pitTag);

    Optional<Peixe> findByInativoFalseAndPitTag(String pitTag);

    List<Peixe> findAllByPitTag(String pitTag);
    // ----------------------------------------------

    // List<Peixe> findAllByInativoFalseOrderByIdAsc(Pageable page);

    List<Peixe> findAllByInativoFalseOrderByIdAsc();

    List<Peixe> findAllNomeCientificoByInativoFalseOrderByIdAsc();

    List<Peixe> findByInativoFalseAndNomeCientificoOrderByIdAsc(String nomeCientifico, Pageable page);

    List<Peixe> findByInativoFalseAndLocalCapturaOrderByIdAsc(String localCaptura, Pageable page);

    List<Peixe> findByInativoFalseAndDataSolturaBetweenOrderByIdAsc(LocalDate dataInicio, LocalDate dataFim, Pageable page);

    List<Peixe> findByInativoFalseAndLocalSolturaOrderByIdAsc(String localSoltura, Pageable page);

    Optional<Peixe> findByInativoFalseAndAmostraDnaOrderByIdAsc(String amostraDna);

    // List<Peixe> findByInativoFalseAndRecapturaOrderByIdAsc(Boolean recaptura,
    // Pageable page);

    List<Peixe> findByInativoFalseAndRecapturaOrderByIdAsc(Boolean recaptura);

}
