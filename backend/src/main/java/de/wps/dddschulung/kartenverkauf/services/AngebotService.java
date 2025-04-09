package de.wps.dddschulung.kartenverkauf.services;

import de.wps.dddschulung.kartenverkauf.api.mappers.PlatzMapper;
import de.wps.dddschulung.kartenverkauf.api.mappers.PlatzMapperImpl;
import de.wps.dddschulung.kartenverkauf.api.model.PlatzDto;
import de.wps.dddschulung.kartenverkauf.domain.Angebot;
import de.wps.dddschulung.kartenverkauf.domain.Platzbelegungen;
import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.dddschulung.kartenverkauf.persistence.repositories.VorstellungRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AngebotService {
    private final SaalplanStapel saalplanStapel;
    private final VorstellungRepository vorstellungRepository;
    private final PlatzMapper platzMapper = new PlatzMapperImpl();

    public Angebot holeAngebot(int platzanzahl, String vorstellungUuid) {
        UUID uuid = UUID.fromString(vorstellungUuid);
        Saalplan saalplan = saalplanStapel.holeSaalplan(uuid);
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
        Platzbelegungen platzbelegungen = saalplan.holePlatzbelegungen(zusammenhaengendePlaetze);
        List<Platz> plaetze = zusammenhaengendePlaetze.plaetze();

        if (plaetze.isEmpty()) {
            return new Angebot(new Geldbetrag(0), platzbelegungen, null);
        }

        List<PlatzDto> platzDtos = plaetze.stream().map(platzMapper::platzToPlatzDto).toList();
        Geldbetrag gesamtbetrag = new Geldbetrag(this.vorstellungRepository.findEintrittspreisByUuid(uuid) * platzanzahl);

        return new Angebot(gesamtbetrag, platzbelegungen, platzDtos);
    }

}
