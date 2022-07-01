package br.org.pti.piracema.entities.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeixeBlockRegistrationDTO {

    private List<PeixeBlockRegistrationRepeatableDTO> repetivel;
    private List<PeixeBlockRegistrationSingleDTO> unico;

}
