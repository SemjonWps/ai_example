package de.wps.ddd.kino.kartenverkauf.application.domain.kartenausstellung;

import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import org.jmolecules.ddd.annotation.Factory;
import org.springframework.stereotype.Component;

import java.util.List;

@Factory
@Component
public class KartenBlock {
    public List<Kinokarte> erstelleKarten(Vorstellung vorstellung, ZusammenhaengendePlaetze gewaehltePlaetze) {
        return gewaehltePlaetze.plaetze().stream().map(platzId -> new Kinokarte(
                null,
                vorstellung.getFilm(),
                vorstellung.getBeginn(),
                vorstellung.getSaal(),
                platzId.reihe(),
                platzId.platz()
        )).toList();
    }
}
