package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import org.mapstruct.Mapper;

@Mapper
public class VorstellungMapper {

    public Vorstellung toDomain(VorstellungEntity entity) {
        return new Vorstellung(
                new VorstellungId(entity.getUuid()),
                new Saal(entity.getSaal()),
                new Beginn(entity.getBeginn()),
                new Filmname(entity.getFilm()),
                Geldbetrag.euroInCent(entity.getEintrittspreis())
        );
    }
}
