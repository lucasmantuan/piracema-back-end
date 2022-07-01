package br.org.pti.piracema.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeixeBlockRegistrationSingleDTO {

    private String pitTag;
    private Double comprimentoTotal;
    private Double pesoSoltura;
    private String amostraDna;
    private Boolean recaptura;

}
