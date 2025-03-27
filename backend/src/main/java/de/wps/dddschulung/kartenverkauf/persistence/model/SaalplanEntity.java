package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaalplanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime anfangszeit;
    @ManyToOne
    @JoinColumn(name = "saal_id")
    private SaalEntity saal;
    @OneToMany
    private List<PlatzEntity> plaetze;

    public List<ZusammenhaengendePlaetzeEntity> sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var zusammenhaengendePlaetzeListe = new ArrayList<ZusammenhaengendePlaetzeEntity>();
        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetzeEntity(1L, Arrays.asList(this.plaetze.get(0), this.plaetze.get(1)));
        zusammenhaengendePlaetzeListe.add(zusammenhaengendePlaetze);
        return zusammenhaengendePlaetzeListe;
    }

    public void markiereAlsVerkauft(ZusammenhaengendePlaetzeEntity zusammenhaengendePlaetze) {
        for (PlatzEntity p : zusammenhaengendePlaetze.getPlaetze()) {
//            p.markiereAlsVerkauft();
        }
    }
}
