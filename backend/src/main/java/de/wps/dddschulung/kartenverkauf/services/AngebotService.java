package de.wps.dddschulung.kartenverkauf.services;

import de.wps.dddschulung.kartenverkauf.domain.Angebot;
import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.dddschulung.kartenverkauf.persistence.repositories.VorstellungRepository;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AngebotService {
    private final SaalplanStapel saalplanStapel;
    private final VorstellungRepository vorstellungRepository;

    public Angebot holeAngebot(int platzanzahl, String vorstellungUuid) {

        UUID uuid = UUID.fromString(vorstellungUuid);
        Saalplan saalplan = saalplanStapel.holeSaalplan(uuid);
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
        SitzplatzStatus[][] platzbelegung = saalplan.holePlatzbelegungen(zusammenhaengendePlaetze);
        // TODO
        Geldbetrag gesamtbetrag = new Geldbetrag(this.vorstellungRepository.findEintrittspreisByUuid(uuid) * platzanzahl);

    }

}
