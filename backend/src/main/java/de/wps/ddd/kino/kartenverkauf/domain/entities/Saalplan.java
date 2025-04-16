package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import lombok.Getter;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class Saalplan {
    private final Long id;
    private final UUID vorstellungUUID;
    private final Map<Reihennummer, List<Platz>> plaetze;

    public Saalplan(Long id, UUID vorstellungUUID, List<Platz> plaetze) {
        this.id = id;
        this.vorstellungUUID = vorstellungUUID;
        this.plaetze = plaetze.stream().collect(groupingBy(Platz::getReihennummer));
    }

    /**
     * @param anzahlPlaetze die Anzahl der gewünschten freien zusammenhängenden Plätze
     * @return die ersten freien zusammenhängenden Plätze startend von der hintersten Reihe oder eine leere Liste, wenn es keine anzahlPlaetze zusammenhängende Plätze gibt
     */
    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var result = new ArrayList<Platz>();

        for (var reihe : plaetze.values().stream().sorted(Comparator.<List<Platz>>comparingInt(plaetze -> plaetze.getFirst().getReihennummer().nummer()).reversed()).toList()) {
            for (Platz platz : reihe) {
                if (platz.istFrei()) {
                    result.add(platz);
                    if (result.size() == anzahlPlaetze) {
                        return new ZusammenhaengendePlaetze(result);
                    }
                } else {
                    result.clear();
                }
            }
            result.clear();
        }
        return new ZusammenhaengendePlaetze(result);
    }

    public void markiereAlsVerkauft(ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        for (Platz p : zusammenhaengendePlaetze.plaetze()) {
            p.markiereAlsVerkauft();
        }
    }


    public void markiereAlsReserviert(ZusammenhaengendePlaetze zusammenhaengendePlaetze, Reservierungsnummer reservierungsnummer) {
        zusammenhaengendePlaetze.plaetze().forEach(platz -> platz.markiereAlsReserviert(reservierungsnummer));
    }

    public void markiereAlsVerkauft(Reservierungsnummer reservierungsnummer) {
        plaetze.forEach((reihe, plaetzeListe) -> plaetzeListe
                .stream()
                .filter(platz -> Objects.equals(platz.getReservierungsnummer(), reservierungsnummer))
                .forEach(Platz::markiereAlsVerkauft));
    }

    public void gebeNichtAbgeholteReservierungenFrei() {
        plaetze.forEach((reihe, plaetzeListe) -> plaetzeListe
                .stream()
                .filter(platz -> !platz.isIstVerkauft())
                .forEach(Platz::gebeReservierungFrei));
    }
}
