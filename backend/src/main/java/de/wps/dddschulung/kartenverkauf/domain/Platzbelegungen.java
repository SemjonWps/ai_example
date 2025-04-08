package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Platzbelegungen {
    private final SitzplatzStatus[][] platzbelegungen;

    public Platzbelegungen(Map<Reihe, List<Platz>> plaetze) {
        List<Platz> plaetzeInReihe = plaetze.values().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Keine Plätze in Saalplan vorhanden."));
        int reihenzahl = plaetze.size();
        int plaetzeProReihe = plaetzeInReihe.size();
        platzbelegungen = new SitzplatzStatus[reihenzahl][plaetzeProReihe];

        plaetze.forEach((reihe, plaetzeListe) -> {
            plaetzeListe.forEach(platz -> {
                int reihennummer = platz.getReihe().reihennummer();
                int platznummer = platz.getSitz().platznummer();
                platzbelegungen[reihennummer - 1][platznummer - 1] = platz.istBelegt() ? SitzplatzStatus.BELEGT : SitzplatzStatus.FREI;
            });
        });
    }

    public void markiereAlsAngeboten(ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        zusammenhaengendePlaetze.plaetze().forEach(platz -> {
            int reihennummer = platz.getReihe().reihennummer();
            int platznummer = platz.getSitz().platznummer();
            platzbelegungen[reihennummer - 1][platznummer - 1] = SitzplatzStatus.ANGEBOTEN;
        });
    }
}
