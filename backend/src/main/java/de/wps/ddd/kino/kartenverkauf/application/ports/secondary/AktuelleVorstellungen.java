package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import org.jmolecules.ddd.annotation.Repository;

import java.util.List;

@Repository
public interface AktuelleVorstellungen {
    List<Vorstellung> alleVorstellungen();
    Vorstellung holeVorstellung(VorstellungId vorstellungId);
    void hinzufuegen(Vorstellung neueVorstellung);
}
