package de.wps.ddd.kino.kartenverkauf.adapters.primary.event.mappers;

import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.*;

public class FilmHinzugefuegtEventMapper {

    public static VorstellungEntity map(FilmHinzugefuegtEvent event) {
        return new VorstellungEntity(
                event.getUuid(),
                event.getBeginn(),
                event.getTitel(),
                event.getTitel(),
                event.getPreis()
        );
    }
}
