package de.wps.dddschulung.kartenverkauf.domain.entities;

import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
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

    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(int anzahlPlaetze) {
        var result = new ArrayList<Platz>();

        for (var reihe : plaetze.values()) {
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
}
