package br.org.pti.piracema.entities.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeixeDTO {

    private Long id;
    private String pitTag;
    private String nomeCientifico;
    private Double comprimentoPadrao;
    private Double comprimentoTotal;
    private String localCaptura;
    private Double pesoSoltura;
    private LocalDate dataSoltura;
    private String localSoltura;
    private String amostraDna;
    private Boolean recaptura;
    private List<PassagemDTO> passagem;

}