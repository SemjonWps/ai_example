package de.wps.ddd.kino.kartenverkauf.services;

import de.wps.ddd.kino.kartenverkauf.api.mappers.PlatzDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.model.AngebotDto;
import de.wps.ddd.kino.kartenverkauf.api.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.persistence.repositories.VorstellungRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AngebotService {
    private final SaalplanStapel saalplanStapel;
    private final VorstellungRepository vorstellungRepository;
    private final PlatzDtoMapper platzDtoMapper;
    private final SaalplanDtoMapper saalplanDtoMapper;

    public AngebotDto holeAngebot(int platzanzahl, String vorstellungUuid) {
        UUID uuid = UUID.fromString(vorstellungUuid);
        Saalplan saalplan = saalplanStapel.holeSaalplan(uuid);
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
        SaalplanDto saalplanDto = saalplanDtoMapper.saalplantoSaalplanDto(saalplan);
        List<Platz> plaetze = zusammenhaengendePlaetze.plaetze();

        if (plaetze.isEmpty()) {
            return new AngebotDto(new GeldbetragDto(0, "EUR"), saalplanDto, null);
        }

        List<PlatzDto> platzDtos = plaetze.stream().map(platzDtoMapper::platzToPlatzDto).toList();
        var gesamtbetrag = new GeldbetragDto(this.vorstellungRepository.findEintrittspreisByUuid(uuid) * platzanzahl, "EUR");

        return new AngebotDto(gesamtbetrag, saalplanDto, platzDtos);
    }

}
