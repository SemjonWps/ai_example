package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class VorstellungEntity {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "saal_id")
    private SaalEntity saal;
    private LocalDateTime anfangszeit;
}
