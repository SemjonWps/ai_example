package de.wps.dddschulung.kartenverkauf.domain.domainobjects;

import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

@Getter
public class Saalplan {
    private final Long id;
    private final Vorstellung vorstellung;
    private final Map<Reihe, List<Platz>> plaetze;

    public Saalplan(Long id, Vorstellung vorstellung, List<Platz> plaetze) {
        this.id = id;
        this.vorstellung = vorstellung;
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
