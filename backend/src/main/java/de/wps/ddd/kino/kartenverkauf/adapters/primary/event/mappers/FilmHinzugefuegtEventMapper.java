package de.wps.ddd.kino.kartenverkauf.adapters.primary.event.mappers;

import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.*;
import java.time.*;

public class FilmHinzugefuegtEventMapper {

    public static VorstellungEntity map(FilmHinzugefuegtEvent event, LocalDateTime beginn, String saal) {
        var vorstellung = new VorstellungEntity(
            event.getUuid(),
                beginn,
                saal,
            event.getTitel(),
            event.getPreis()
        );
        return vorstellung;
    }
}
