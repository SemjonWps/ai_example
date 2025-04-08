package de.wps.dddschulung.kartenverkauf.domain.entities;

import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.dddschulung.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import lombok.Getter;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class Saalplan {
    private final Long id;
    private final UUID vorstellungUUID;
    private final Map<Reihe, List<Platz>> plaetze;

    public Saalplan(Long id, UUID vorstellungUUID, List<Platz> plaetze) {
        this.id = id;
        this.vorstellungUUID = vorstellungUUID;
        this.plaetze = plaetze.stream().collect(groupingBy(Platz::getReihe));
    }

    /**
     * @param anzahlPlaetze die Anzahl der gewünschten freien zusammenhängenden Plätze
     * @return die ersten freien zusammenhängenden Plätze startend von der hintersten Reihe oder eine leere Liste, wenn es keine anzahlPlaetze zusammenhängende Plätze gibt
     */
    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var result = new ArrayList<Platz>();

        for (var reihe : plaetze.values().stream().sorted(Comparator.<List<Platz>>comparingInt(plaetze -> plaetze.getFirst().getReihe().reihennummer()).reversed()).toList()) {
            for (Platz platz : reihe) {
                if (platz.istFrei()) {
                    result.add(platz);
                    if (result.size() == anzahlPlaetze) {
                        return new ZusammenhaengendePlaetze(result);
                    }
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
                .filter(platz -> !platz.istVerkauft())
                .forEach(Platz::gebeReservierungFrei));
    }

    /**
     * Berechnet die Platzbelegung unter Berücksichtigung der angefragten zusammenhängenden Plätze. <br>
     * requirements: <br>
     * - Zusammenhängende Plätze dürfen nicht belegt sein. <br>
     * - Saalplan muss mindestens einen Platz enthalten.
     *
     * @param zusammenhaengendePlaetze Die angefragten Plätze
     * @return Platzbelegung
     * @throws IllegalStateException    Wenn Saalplan keine Plätze enthält
     * @throws IllegalArgumentException Wenn zusammenhaendePlaetze belegte Plätze enthält
     */
    public SitzplatzStatus[][] holePlatzbelegungen(ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        if (zusammenhaengendePlaetze.plaetze().stream().anyMatch(Platz::istBelegt)) {
            throw new IllegalArgumentException("Nicht alle zusammenhängenden Plätze sind frei.");
        }
        List<Platz> plaetzeInReihe = plaetze.values().stream().findFirst().orElseThrow(() -> new IllegalStateException("Keine Plätze in Saalplan vorhanden."));
        int reihenzahl = plaetze.size();
        int plaetzeProReihe = plaetzeInReihe.size();
        SitzplatzStatus[][] platzbelegungen = new SitzplatzStatus[reihenzahl][plaetzeProReihe];

        plaetze.forEach((reihe, plaetzeListe) -> {
            plaetzeListe.forEach(platz -> {
                int reihennummer = platz.getReihe().reihennummer();
                int platznummer = platz.getSitz().platznummer();
                platzbelegungen[reihennummer][platznummer] = platz.istBelegt() ? SitzplatzStatus.BELEGT : SitzplatzStatus.FREI;
            });
        });

        zusammenhaengendePlaetze.plaetze().forEach(platz -> {
            int reihennummer = platz.getReihe().reihennummer();
            int platznummer = platz.getSitz().platznummer();
            platzbelegungen[reihennummer][platznummer] = SitzplatzStatus.ANGEBOTEN;
        });

        return platzbelegungen;
    }
}
