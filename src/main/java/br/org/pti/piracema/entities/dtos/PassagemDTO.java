package br.org.pti.piracema.entities.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassagemDTO {

    private Long id;
    private LocalDateTime dataPassagem;
    private PeixeNoPassagemDTO peixe;
    private AntenaNoPassagemAndStatusAntenaDTO antena;

}
