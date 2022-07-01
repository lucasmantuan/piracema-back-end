package br.org.pti.piracema.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import br.org.pti.piracema.entities.dtos.PassagemImportFileDTO;
import br.org.pti.piracema.services.exceptions.DataIntegrationViolation;

@Service
@Transactional
public class PassagemImportFileService {

    public List<PassagemImportFileDTO> parse(MultipartFile file) throws IOException {
        try {

            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            CsvToBean<PassagemImportFileDTO> csvToBean = new CsvToBeanBuilder(csvReader).withSeparator(',')
                    .withType(PassagemImportFileDTO.class).build();
            List<PassagemImportFileDTO> passagemImportFileDTO = csvToBean.parse();

            return passagemImportFileDTO;

        } catch (Exception e) {
            throw new DataIntegrationViolation(e.getMessage());
        }
    }
}