package de.wps.ddd.kino.kartenverkauf.services;

import de.wps.ddd.kino.kartenverkauf.api.mappers.PlatzDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.PlatzDtoMapperImpl;
import de.wps.ddd.kino.kartenverkauf.api.model.AngebotDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.Platzbelegungen;
import de.wps.ddd.kino.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
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
    private final PlatzDtoMapper platzDtoMapper = new PlatzDtoMapperImpl();

    public AngebotDto holeAngebot(int platzanzahl, String vorstellungUuid) {
        UUID uuid = UUID.fromString(vorstellungUuid);
        Saalplan saalplan = saalplanStapel.holeSaalplan(uuid);
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
        Platzbelegungen platzbelegungen = saalplan.holePlatzbelegungen(zusammenhaengendePlaetze);
        List<Platz> plaetze = zusammenhaengendePlaetze.plaetze();

        if (plaetze.isEmpty()) {
            return new AngebotDto(new Geldbetrag(0), platzbelegungen, null);
        }

        List<PlatzDto> platzDtos = plaetze.stream().map(platzDtoMapper::platzToPlatzDto).toList();
        Geldbetrag gesamtbetrag = new Geldbetrag(this.vorstellungRepository.findEintrittspreisByUuid(uuid) * platzanzahl);

        return new AngebotDto(gesamtbetrag, platzbelegungen, platzDtos);
    }

}
