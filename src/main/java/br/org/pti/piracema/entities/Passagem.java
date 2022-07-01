package br.org.pti.piracema.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "passagens")
public class Passagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    @Column(nullable = false)
    private LocalDateTime dataPassagem;

    @ManyToOne
    @JoinColumn(name = "id_peixe")
    private Peixe peixe;

    @ManyToOne
    @JoinColumn(name = "id_antena")
    private Antena antena;

    @PrePersist
    public void create() {
        dataRegistro = LocalDateTime.now();
    }

    @PreUpdate
    public void update() {
        dataRegistro = LocalDateTime.now();
    }

}
