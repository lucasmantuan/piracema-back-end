package br.org.pti.piracema.entities.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeixeFormDTO {

    private LocalDate dataInicioConsulta;
    private LocalDate dataFimConsulta;
    private String amostraDna;

}