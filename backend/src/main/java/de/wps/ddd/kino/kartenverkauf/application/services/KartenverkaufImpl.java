package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.factories.KartenBlock;
import de.wps.ddd.kino.kartenverkauf.application.domain.services.Preisberechnung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Kartenverkauf;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class KartenverkaufImpl implements Kartenverkauf {

    private final AktuelleVorstellungen aktuelleVorstellungen;

    private final Preisberechnung preisberechnung;

    private final KartenBlock kartenBlock;

    private final SaalplanStapel saalplanStapel;

    private final Zahlung zahlung;

    @Override
    public Vorstellung holeVorstellung(VorstellungId vorstellungId) {
        return aktuelleVorstellungen.holeVorstellung(vorstellungId);
    }

    @Override
    public Saalplan holeSaalplan(VorstellungId vorstellungId) {
        return saalplanStapel.holeSaalplan(vorstellungId);
    }

    @Override
    public ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(VorstellungId vorstellungId, int platzanzahl) {
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        return saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
    }

    @Override
    public Geldbetrag berechneGesamtpreis(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        var vorstellung = aktuelleVorstellungen.holeVorstellung(vorstellungId);
        return preisberechnung.ermittlePreis(vorstellung, zusammenhaengendePlaetze);
    }

    @Override
    public List<Kinokarte> erstelleKinokarten(Auftragsnummer auftragsnummer, VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze) {
        Assert.isTrue(zahlung.status(auftragsnummer).equals(Zahlungsstatus.Eingegangen), "Zahlung ist noch nicht eingegangen");
        var vorstellung = aktuelleVorstellungen.holeVorstellung(vorstellungId);
        var kinokarten = kartenBlock.erstelleKarten(vorstellung, gewaehltePlaetze);
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        saalplan.markiereAlsVerkauft(gewaehltePlaetze);
        saalplanStapel.legeZurueck(saalplan);
        return kinokarten;
    }
}
