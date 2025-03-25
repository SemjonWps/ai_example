package de.wps.dddschulung.kartenverkauf.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Saalplan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime datum;
    @ManyToOne
    @JoinColumn(name = "saal_id")
    private Saal saal;
    @OneToMany
    private List<ZusammenhaengendePlaetze> zusammenhaengendePlaetze;

    public List<ZusammenhaengendePlaetze> sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        return null;
    }
}
