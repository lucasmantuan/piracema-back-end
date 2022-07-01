package br.org.pti.piracema.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "peixes")
public class Peixe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    // @Column(nullable = false, unique = true)
    @Column(nullable = false)
    @Pattern(regexp = "^[a-fA-F0-9]{1,8}$", message = "The PitTag value is not valid")
    private String pitTag;

    @Column(nullable = false)
    private String nomeCientifico;

    @Column(nullable = false)
    private Double comprimentoPadrao;

    @Column(nullable = false)
    private Double comprimentoTotal;

    @Column(nullable = false)
    private String localCaptura;

    @Column(nullable = false)
    private Double pesoSoltura;

    @Column(nullable = false)
    private LocalDate dataSoltura;

    @Column(nullable = false)
    private String localSoltura;

    @Column(nullable = false)
    private String amostraDna;

    @Column
    private Boolean recaptura;

    @Column
    private Boolean inativo;

    @OneToMany(mappedBy = "peixe", cascade = { CascadeType.ALL })
    private List<Passagem> passagem;

    @PrePersist
    public void create() {
        dataRegistro = LocalDateTime.now();
        inativo = false;
    }

    @PreUpdate
    public void update() {
        dataRegistro = LocalDateTime.now();
    }

}
