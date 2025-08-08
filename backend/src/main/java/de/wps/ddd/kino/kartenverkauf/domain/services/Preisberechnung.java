package de.wps.ddd.kino.kartenverkauf.domain.services;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class Preisberechnung {
    public Geldbetrag ermittlePreis(Vorstellung vorstellung, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        // TODO: Platzkategorie, Überlänge, 3D, usw.
        return vorstellung.getEintrittspreis().mal(zusammenhaengendePlaetze.anzahl());
    }

}
