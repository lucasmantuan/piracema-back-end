package br.org.pti.piracema.entities.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeixeBlockRegistrationRepeatableDTO {

    private String nomeCientifico;
    private Double comprimentoPadrao;
    private String localCaptura;
    private LocalDate dataSoltura;
    private String localSoltura;

}
