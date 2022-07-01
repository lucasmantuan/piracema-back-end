package br.org.pti.piracema.entities.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusAntenaFormDTO {

    private Boolean status;
    private LocalDate dataMudancaStatus;
    private Long antenaId;

}
