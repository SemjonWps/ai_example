package de.wps.dddschulung.kartenverkauf.services;

import de.wps.dddschulung.kartenverkauf.domain.Angebot;
import de.wps.dddschulung.kartenverkauf.domain.Platzbelegungen;
import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.persistence.repositories.VorstellungRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class AngebotService {
    private final SaalplanStapel saalplanStapel;
    private final VorstellungRepository vorstellungRepository;

    public Angebot holeAngebot(int platzanzahl, String vorstellungUuid) {
        UUID uuid = UUID.fromString(vorstellungUuid);
        Saalplan saalplan = saalplanStapel.holeSaalplan(uuid);
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
        List<Platz> plaetze = zusammenhaengendePlaetze.plaetze();
        Platzbelegungen platzbelegungen = saalplan.holePlatzbelegungen(zusammenhaengendePlaetze);

        if (plaetze.isEmpty()) {
            return new Angebot(new Geldbetrag(0), platzbelegungen, null, null);
        }

        Reihe reihe = plaetze.getFirst().getReihe();
        Geldbetrag gesamtbetrag = new Geldbetrag(this.vorstellungRepository.findEintrittspreisByUuid(uuid) * platzanzahl);

        return new Angebot(gesamtbetrag, platzbelegungen, reihe, zusammenhaengendePlaetze);
    }

}
