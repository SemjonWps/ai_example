package de.wps.ddd.kino.kartenverkauf.adapters.primary.event.mappers;

import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.*;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.*;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.*;
import java.util.*;

public class FilmHinzugefuegtEventMapper {

    public static Vorstellung map(FilmHinzugefuegtEvent event) {
        return new Vorstellung(
                new VorstellungId(event.getUuid()),
                new Saal(event.getSaal()),
                new Beginn(event.getBeginn()),
                new Film(event.getTitel()),
                Geldbetrag.of(event.getPreis(), Geldbetrag.Waehrung.EUR)
        );
    }
}
