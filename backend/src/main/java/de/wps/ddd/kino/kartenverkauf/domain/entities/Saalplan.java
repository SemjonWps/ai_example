package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.*;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Saalplan {
    private final Long id;
    private final UUID vorstellungUUID;
    private final SortedMap<Reihennummer, SortedMap<Platznummer, Platz>> plaetze;

    public Saalplan(Long id, UUID vorstellungUUID, List<Platz> plaetze) {
        this.id = id;
        this.vorstellungUUID = vorstellungUUID;
        this.plaetze = plaetze.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPlatzId().reihennummer(),
                        TreeMap::new, // outer map - sorted by Reihennummer
                        Collectors.toMap(
                                p -> p.getPlatzId().platznummer(),
                                Function.identity(),
                                (p1, p2) -> p1, // handle duplicate keys if needed
                                TreeMap::new    // inner map - sorted by Platznummer
                        )
                ));
    }

    /**
     * @param anzahlPlaetze die Anzahl der gewünschten freien zusammenhängenden Plätze
     * @return die ersten freien zusammenhängenden Plätze startend von der hintersten Reihe oder eine leere Liste, wenn es keine anzahlPlaetze zusammenhängende Plätze gibt
     */
    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var result = new ArrayList<PlatzId>();

        for (var reihe : plaetze.values()) {
            for (Platz platz : reihe.values()) {
                if (platz.istFrei()) {
                    result.add(platz.getPlatzId());
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
        for (PlatzId p : zusammenhaengendePlaetze.plaetze()) {
            var platz = plaetze.get(p.reihennummer()).get(p.platznummer());
            platz.markiereAlsVerkauft();
        }
    }


    public void markiereAlsReserviert(ZusammenhaengendePlaetze zusammenhaengendePlaetze, Reservierungsnummer reservierungsnummer) {
        for (PlatzId p : zusammenhaengendePlaetze.plaetze()) {
            var platz = plaetze.get(p.reihennummer()).get(p.platznummer());
            platz.markiereAlsReserviert(reservierungsnummer);
        }
    }

    public void markiereAlsVerkauft(Reservierungsnummer reservierungsnummer) {
        allePlaetze()
                .filter(platz -> Objects.equals(platz.getReservierungsnummer(), reservierungsnummer))
                .forEach(Platz::markiereAlsVerkauft);
    }

    public void gebeNichtAbgeholteReservierungenFrei() {
        allePlaetze()
                .filter(platz -> !platz.isIstVerkauft())
                .forEach(Platz::gebeReservierungFrei);
    }

    private Stream<Platz> allePlaetze() {
        return plaetze.values().stream().flatMap(innerMap -> innerMap.values().stream());
    }
}
