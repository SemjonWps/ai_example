package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VorstellungEntity {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "saal_id")
    private SaalEntity saal;
    private LocalDateTime anfangszeit;
}
