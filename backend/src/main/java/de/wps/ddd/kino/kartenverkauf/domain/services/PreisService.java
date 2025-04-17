package de.wps.ddd.kino.kartenverkauf.domain.services;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import org.springframework.stereotype.Service;

@Service
public class PreisService {
    public Geldbetrag ermittlePreis(Vorstellung vorstellung, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        return vorstellung.getEintrittspreis().mal(zusammenhaengendePlaetze.anzahl());
    }
}
