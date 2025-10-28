package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.VorstellungId;
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
