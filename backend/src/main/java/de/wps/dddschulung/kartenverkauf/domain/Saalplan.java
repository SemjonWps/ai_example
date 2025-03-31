package de.wps.dddschulung.kartenverkauf.domain;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class Saalplan {
    private final Long id;
    private final Vorstellung vorstellung;
    private final Map<Integer, List<Platz>> plaetze;

    public Saalplan(Long id, Vorstellung vorstellung, List<Platz> plaetze) {
        this.id = id;
        this.vorstellung = vorstellung;
        this.plaetze = plaetze.stream().collect(groupingBy(Platz::getReihe));
    }

    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var result = new ZusammenhaengendePlaetze();

        for (var reihe : plaetze.values()) {
            for (Platz platz : reihe) {
                if (platz.istFrei()) {
                    result.plaetze.add(platz);
                    if (result.plaetze.size() == anzahlPlaetze) {
                        return result;
                    }
                }
            }
            result.plaetze.clear();
        }
        return result;
    }

    public void markiereAlsVerkauft(ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        for (Platz p : zusammenhaengendePlaetze.getPlaetze()) {
            p.markiereAlsVerkauft();
        }
    }


    public void markiereAlsReserviert(ZusammenhaengendePlaetze zusammenhaengendePlaetze, String reservierungsnummer) {
        zusammenhaengendePlaetze.getPlaetze().forEach(platz -> platz.markiereAlsReserviert(reservierungsnummer));
    }

    public void markiereAlsVerkauft(String reservierungsnummer) {
        plaetze.forEach((reihe, plaetzeListe) -> plaetzeListe
                .stream()
                .filter(platz -> Objects.equals(platz.getReservierungsnummer(), reservierungsnummer))
                .forEach(Platz::markiereAlsVerkauft));
    }

    public void gebeNichtAbgeholteReservierungenFrei() {
        plaetze.forEach((reihe, plaetzeListe) -> plaetzeListe
                .stream()
                .filter(platz -> !platz.istVerkauft())
                .forEach(Platz::gebeReservierungFrei));
    }
}
