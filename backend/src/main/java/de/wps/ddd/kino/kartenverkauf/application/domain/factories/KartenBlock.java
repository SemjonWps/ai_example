package de.wps.ddd.kino.kartenverkauf.application.domain.factories;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;
import org.jmolecules.ddd.annotation.Factory;
import org.springframework.stereotype.Component;

import java.util.List;

@Factory
@Component
public class KartenBlock {
    public List<Kinokarte> erstelleKarten(Vorstellung vorstellung, ZusammenhaengendePlaetze gewaehltePlaetze) {
        return gewaehltePlaetze.plaetze().stream().map(platzId -> new Kinokarte(
                vorstellung.getFilm(),
                vorstellung.getBeginn(),
                vorstellung.getSaal(),
                platzId.reihe(),
                platzId.platz()
        )).toList();
    }
}
