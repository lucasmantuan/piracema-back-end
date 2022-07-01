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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "antenas")
public class Antena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    @Column(nullable = false)
    private LocalDate dataInstalacao;

    @Column
    private LocalDate dataDesativacao;

    @OneToMany(mappedBy = "antena")
    private List<Passagem> passagem;

    @OneToMany(mappedBy = "antena", cascade = { CascadeType.ALL })
    private List<StatusAntena> statusAntena;

    @PrePersist
    public void create() {
        dataRegistro = LocalDateTime.now();
    }

    @PreUpdate
    public void update() {
        dataRegistro = LocalDateTime.now();
    }

}
