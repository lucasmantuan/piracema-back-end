package br.org.pti.piracema.entities;

import java.time.LocalDate;
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
@Table(name = "statusantenas")
public class StatusAntena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    @Column(nullable = false)
    private Boolean status;

    @Column(nullable = false)
    private LocalDate dataMudancaStatus;

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
