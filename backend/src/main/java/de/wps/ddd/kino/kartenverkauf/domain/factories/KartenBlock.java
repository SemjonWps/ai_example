package de.wps.ddd.kino.kartenverkauf.domain.factories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KartenBlock {
    public List<Kinokarte> erstelleKarten(Vorstellung vorstellung, ZusammenhaengendePlaetze gewaehltePlaetze) {
        return gewaehltePlaetze.plaetze().stream().map(platz -> new Kinokarte(
                vorstellung, platz, vorstellung.getEintrittspreis()
        )).toList();
    }
}
