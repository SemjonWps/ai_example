package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;

public class SaalplanDtoMapper {

    public SaalplanDto saalplantoSaalplanDto(Saalplan saalplan) {
        return null;
    }

    ;

    /*List<Platz> plaetzeInReihe = plaetze.values().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Keine Plätze in Saalplan vorhanden."));
    int plaetzeProReihe = plaetzeInReihe.size();
    int reihenzahl = plaetze.size();
    platzbelegungen = new PlatzDto[reihenzahl][plaetzeProReihe];

        plaetze.forEach((reihe, plaetzeListe) -> {
        plaetzeListe.forEach(platz -> {
            int nummer = platz.getReihe().nummer();
            int nummer = platz.getSitz().nummer();
            platzbelegungen[nummer - 1][nummer - 1] = platz.istBelegt() ? SitzplatzStatus.BELEGT : SitzplatzStatus.FREI;
        });
    });*/
}
