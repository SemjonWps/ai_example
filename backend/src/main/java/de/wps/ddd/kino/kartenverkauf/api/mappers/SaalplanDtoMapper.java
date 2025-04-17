package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaalplanDtoMapper {
    PlatzDtoMapper platzDtoMapper;

    public SaalplanDto saalplantoSaalplanDto(Saalplan saalplan) {
        /*var plaetze = saalplan.getPlaetze();

        List<Platz> plaetzeInReihe = plaetze.values().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("Keine Plätze in Saalplan vorhanden."));
        int plaetzeProReihe = plaetzeInReihe.size();
        int reihenzahl = plaetze.size();
        PlatzDto[][] platzbelegungen = new PlatzDto[reihenzahl][plaetzeProReihe];

        plaetze.forEach((reihe, plaetzeListe) -> plaetzeListe.forEach(platz -> {
            int reihennummer = platz.getReihennummer().nummer();
            int platznummer = platz.getPlatznummer().nummer();
            platzbelegungen[reihennummer - 1][platznummer - 1] = platzDtoMapper.platzToPlatzDto(platz);
        }));

        return new SaalplanDto(platzbelegungen);*/
        return null;
    }
}
