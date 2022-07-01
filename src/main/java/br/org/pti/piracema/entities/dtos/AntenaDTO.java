package br.org.pti.piracema.entities.dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AntenaDTO {

    private Long id;
    private String latitude;
    private String longitude;
    private LocalDate dataInstalacao;
    private LocalDate dataDesativacao;
    private List<PassagemDTO> passagem;
    private List<StatusAntenaDTO> statusAntena;

}
