package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import org.mapstruct.Mapper;

@Mapper
public class VorstellungMapper {

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
