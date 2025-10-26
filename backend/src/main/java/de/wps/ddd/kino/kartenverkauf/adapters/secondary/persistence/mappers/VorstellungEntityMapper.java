package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.VorstellungId;
import org.springframework.stereotype.Component;

@Component
public class VorstellungEntityMapper {

    public Vorstellung toDomain(VorstellungEntity entity) {
        return new Vorstellung(
                new VorstellungId(entity.getUuid()),
                new Saal(entity.getSaal()),
                new Beginn(entity.getBeginn()),
                new Film(entity.getFilm()),
                Geldbetrag.euroInCent(entity.getEintrittspreis())
        );
    }
}
