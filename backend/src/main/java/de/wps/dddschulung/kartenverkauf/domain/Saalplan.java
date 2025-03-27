package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Saalplan {
    private Long id;
    private LocalDateTime anfangszeit;
    private Saal saal;
    private List<Platz> plaetze;

    public List<ZusammenhaengendePlaetze> sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var zusammenhaengendePlaetzeListe = new ArrayList<ZusammenhaengendePlaetze>();
        var tempZusammenhaengendePlaetze = new ZusammenhaengendePlaetze();
        var row = -1;

        for (Platz platz : plaetze) {
            if (!platz.isBelegt()) {
                if (tempZusammenhaengendePlaetze.plaetze.isEmpty() || platz.getReihe() == row) {
                    tempZusammenhaengendePlaetze.plaetze.add(platz);
                } else {
                    if (tempZusammenhaengendePlaetze.plaetze.size() >= anzahlPlaetze) {
                        zusammenhaengendePlaetzeListe.add(tempZusammenhaengendePlaetze);
                    }
                    tempZusammenhaengendePlaetze.plaetze.clear();
                    tempZusammenhaengendePlaetze.plaetze.add(platz);
                }
                row = platz.getReihe();
            } else {
                if (tempZusammenhaengendePlaetze.plaetze.size() >= anzahlPlaetze) {
                    zusammenhaengendePlaetzeListe.add(tempZusammenhaengendePlaetze);
                    tempZusammenhaengendePlaetze.plaetze.clear();
                }
            }
        }
        if (tempZusammenhaengendePlaetze.plaetze.size() >= anzahlPlaetze) {
            zusammenhaengendePlaetzeListe.add(tempZusammenhaengendePlaetze);
        }
        return zusammenhaengendePlaetzeListe;
    }

    public void markiereAlsVerkauft(ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        for (Platz p : zusammenhaengendePlaetze.getPlaetze()) {
            p.markiereAlsVerkauft();
        }
    }

    public LocalDateTime getAnfangszeit() {
        return anfangszeit;
    }

    public Saal getSaal() {
        return saal;
    }
}
