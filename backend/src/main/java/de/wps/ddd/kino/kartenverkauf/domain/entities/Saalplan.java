package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class Saalplan {
    private final Long id;
    private final VorstellungId vorstellungId;
    private final TreeMap<ReiheNummer, TreeMap<PlatzNummer, Platz>> plaetze;

    public Saalplan(Long id, VorstellungId vorstellungId, List<Platz> plaetze) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(vorstellungId, "vorstellungId must not be null");
        this.id = id;
        this.vorstellungId = vorstellungId;
        this.plaetze = plaetze.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPlatzId().reiheNr(),
                        TreeMap::new, // outer map - sorted by Reihennummer
                        Collectors.toMap(
                                p -> p.getPlatzId().platzNr(),
                                Function.identity(),
                                (p1, p2) -> p1, // handle duplicate keys if needed
                                TreeMap::new    // inner map - sorted by Platznummer
                        )
                ));
    }

    /**
     * @param anzahlPlaetze die Anzahl der gewünschten freien zusammenhängenden Plätze
     * @return die ersten freien zusammenhängenden Plätze startend von der hintersten Reihe oder eine leere Liste, wenn es keine anzahlPlaetze zusammenhängenden Plätze gibt
     */
    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var result = new ArrayList<PlatzId>();

        for (var reihe : plaetze.descendingMap().values()) {
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
            platz(p).markiereAlsVerkauft();
        }
    }

    public void markiereAlsReserviert(ZusammenhaengendePlaetze zusammenhaengendePlaetze, Reservierungsnummer reservierungsnummer) {
        for (PlatzId p : zusammenhaengendePlaetze.plaetze()) {
            platz(p).markiereAlsReserviert(reservierungsnummer);
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

    public Platz platz(PlatzId platzId) {
        return this.plaetze.get(platzId.reiheNr()).get(platzId.platzNr());
    }

    private Stream<Platz> allePlaetze() {
        return plaetze.values().stream().flatMap(innerMap -> innerMap.values().stream());
    }
}
