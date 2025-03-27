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
        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze();
        var row = -1;

        for (Platz platz : plaetze) {
            if (!platz.isBelegt()) {
                if (platz.getReihe() == row || row == -1) {
                    zusammenhaengendePlaetze.plaetze.add(platz);
                    row = platz.getReihe();
                }
            } else {
                zusammenhaengendePlaetzeListe.add(new ZusammenhaengendePlaetze(Arrays.asList(new ArrayList<>(zusammenhaengendePlaetze)));
            }

        }
        zusammenhaengendePlaetzeListe.add(zusammenhaengendePlaetze);
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
