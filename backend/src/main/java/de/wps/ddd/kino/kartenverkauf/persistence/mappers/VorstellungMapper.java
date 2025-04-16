package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.persistence.model.VorstellungEntity;
import org.mapstruct.Mapper;

@Mapper
public class VorstellungMapper {

    public Vorstellung toDomain(VorstellungEntity entity) {
        return new Vorstellung(
                entity.getUuid(),
                new Saal(entity.getSaal()),
                new Beginn(entity.getAnfangszeit()),
                new Filmname(entity.getFilmname()),
                Geldbetrag.euroInCent(entity.getEintrittspreis())
        );
    }
}
