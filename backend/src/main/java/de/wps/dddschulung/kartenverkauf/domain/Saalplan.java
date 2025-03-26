package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class Saalplan {
    private Long id;
    private LocalDateTime anfangszeit;
    private Saal saal;
    private List<Platz> plaetze;

    public List<ZusammenhaengendePlaetze> sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var zusammenhaengendePlaetzeListe = new ArrayList<ZusammenhaengendePlaetze>();
        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(1L, Arrays.asList(this.plaetze.get(0), this.plaetze.get(1)));
        zusammenhaengendePlaetzeListe.add(zusammenhaengendePlaetze);
        return zusammenhaengendePlaetzeListe;
    }

    public void markiereAlsVerkauft(ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        for (Platz p : zusammenhaengendePlaetze.getPlaetze()) {
            p.markiereAlsVerkauft();
        }
    }
}
