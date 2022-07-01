package br.org.pti.piracema.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.org.pti.piracema.entities.Antena;
import br.org.pti.piracema.entities.Passagem;
import br.org.pti.piracema.entities.Peixe;
import br.org.pti.piracema.entities.dtos.PassagemImportFileDTO;
import br.org.pti.piracema.repositories.PassagemRepository;
import br.org.pti.piracema.services.exceptions.DataBaseException;
import br.org.pti.piracema.services.exceptions.ResourceNotFoundException;

@Service
@Transactional
public class PassagemService {

    private final PassagemRepository passagemRepository;
    private final PeixeService peixeService;
    private final AntenaService antenaService;
    private final PassagemImportFileService passagemImportFileService;

    public PassagemService(PassagemRepository passagemRepository, PeixeService peixeService,
            AntenaService antenaService, PassagemImportFileService passagemImportFileService) {
        this.passagemRepository = passagemRepository;
        this.peixeService = peixeService;
        this.antenaService = antenaService;
        this.passagemImportFileService = passagemImportFileService;
    }

    // public List<Passagem> upload(MultipartFile file) throws IOException {
    // List<PassagemImportFileDTO> passagemImportFileDTO =
    // passagemImportFileService.parse(file);
    // return passagemImportFileDTO.stream().map(c ->
    // create(c)).collect(Collectors.toList());
    // }

    public List<Passagem> upload(MultipartFile file) throws IOException {

        // salva os elementos do csv em um numa lista
        List<PassagemImportFileDTO> passagemImportFileDTO = passagemImportFileService.parse(file);

        // // identifica os pittag achados nos registros e então são salvos em uma lista
        // List<String> pitTagFound = new ArrayList<>();
        // for (int i = 0; i < passagemImportFileDTO.size(); i++) {
        // pitTagFound.add(passagemImportFileDTO.get(i).getPitTag());
        // System.out.println(passagemImportFileDTO.get(i).getPitTag());
        // }

        // // aplica distinct entre os pittags achados para tirar as repeticoes
        // List<String> distinctElements = pitTagFound.stream()
        // .distinct()
        // .collect(Collectors.toList());

        // // identifica o index onde cada pittag é achado
        // List<Integer> control = new ArrayList<>();
        // for (int i = 0; i < distinctElements.size(); i++) {
        // List<Integer> indexForEachPittag = new ArrayList<>();
        // for (int j = 0; j < passagemImportFileDTO.size(); j++) {
        // if (distinctElements.get(i).equals(passagemImportFileDTO.get(j).getPitTag()))
        // {
        // indexForEachPittag.add(j);
        // } else {
        // indexForEachPittag.add(-1);
        // }
        // }

        // // acha o index maior para cada pitTag
        // Integer moda = 0;
        // for (Integer valor : indexForEachPittag) {
        // if (valor > moda) {
        // moda = valor;
        // }
        // }

        // // lista com os index dos registros que vao ser salvos no banco de dados
        // control.add(indexForEachPittag.indexOf(moda));
        // }

        // // selecao dos registros que vao ser salvos
        // List<PassagemImportFileDTO> passagemImportFileSaveDTO = new ArrayList<>();
        // for (Integer i : control) {
        // passagemImportFileSaveDTO.add(passagemImportFileDTO.get(i));
        // }

        // salva no banco de dados
        return passagemImportFileDTO.stream().map(c -> create(c)).collect(Collectors.toList());

    }

    private Passagem create(PassagemImportFileDTO passagemDTO) {
        Peixe peixe = peixeService.findByInativoFalseAndPitTag(passagemDTO.getPitTag());
        Antena antena = antenaService.findById(Long.valueOf(passagemDTO.getAntenaId()));
        Passagem passagem = new Passagem();
        passagem.setDataPassagem(passagemDTO.getDataPassagem());
        peixe.getPassagem().add(passagem);
        passagem.setPeixe(peixe);
        antena.getPassagem().add(passagem);
        passagem.setAntena(antena);
        return passagemRepository.save(passagem);
    }

    public void deleteById(Long id) {
        try {
            passagemRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public Passagem findById(Long id) {
        Optional<Passagem> passagem = passagemRepository.findById(id);
        return passagem.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<Passagem> findAll() {
        List<Passagem> passagens = passagemRepository.findAllByOrderByIdAsc();
        if (passagens.isEmpty()) {
            throw new ResourceNotFoundException();
        } else {
            return passagens;
        }
    }

    // public List<Passagem> findAll(int pageNumber, int pageSize) {
    //     if (pageSize >= 10)
    //         pageSize = 10;
    //     Pageable page = PageRequest.of(pageNumber, pageSize);
    //     List<Passagem> passagens = passagemRepository.findAllByOrderByIdAsc(page);
    //     if (passagens.isEmpty()) {
    //         throw new ResourceNotFoundException();
    //     } else {
    //         return passagens;
    //     }
    // }

}