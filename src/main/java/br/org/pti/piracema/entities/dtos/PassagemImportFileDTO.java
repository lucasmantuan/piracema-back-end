package br.org.pti.piracema.entities.dtos;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassagemImportFileDTO {

    @CsvBindByName(column = "antenaId")
    private Long antenaId;

    @CsvBindByName(column = "pitTag")
    private String pitTag;

    @CsvBindByName(column = "dataPassagem")
    @CsvDate(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataPassagem;

}