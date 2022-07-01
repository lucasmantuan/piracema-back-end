package br.org.pti.piracema.entities.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusAntenaDTO {

    private Long id;
    private Boolean status;
    private LocalDate dataMudancaStatus;
    private AntenaNoPassagemAndStatusAntenaDTO antena;

}
