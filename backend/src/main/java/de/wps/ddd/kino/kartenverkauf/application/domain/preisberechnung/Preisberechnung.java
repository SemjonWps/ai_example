package de.wps.ddd.kino.kartenverkauf.application.domain.preisberechnung;

import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class Preisberechnung {
    public Geldbetrag ermittlePreis(Vorstellung vorstellung, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        // TODO: Platzkategorie, Überlänge, 3D, usw.
        return vorstellung.getEintrittspreis().mal(zusammenhaengendePlaetze.anzahl().value());
    }

}
