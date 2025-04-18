package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class SaalplanDtoMapper {

    private final PlatzDtoMapper platzDtoMapper;

    public SaalplanDto saalplantoSaalplanDto(Saalplan saalplan) {
        var plaetze = saalplan.getPlaetze();

        var reihenzahl = plaetze.size();
        var ersteReihe = plaetze.values().stream().findFirst();
        var platzAnzahl = ersteReihe.map(Map::size).orElse(0);
        var platzbelegungen = new PlatzDto[reihenzahl][platzAnzahl];

        plaetze.forEach((reiheNr, plaetzeListe) -> plaetzeListe.forEach((platzNr, platz) -> {
            platzbelegungen[reiheNr.nummer() - 1][platzNr.nummer() - 1] = platzDtoMapper.platzToPlatzDto(platz);
        }));

        return new SaalplanDto(platzbelegungen);
    }
}
