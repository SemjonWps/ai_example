package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.kartenverkauf.application.ports.in.Kartenverkauf;
import de.wps.ddd.kino.kartenverkauf.application.ports.out.AktuelleVorstellungen;
import de.wps.ddd.kino.kartenverkauf.application.ports.out.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.factories.KartenBlock;
import de.wps.ddd.kino.kartenverkauf.domain.services.PreisService;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class KartenverkaufImpl implements Kartenverkauf {

    private final AktuelleVorstellungen aktuelleVorstellungen;

    private final PreisService preisService;

    private final KartenBlock kartenBlock;

    private final SaalplanStapel saalplanStapel;

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
    public Zahlungsanforderung fordereBezahlungAn(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        var vorstellung = aktuelleVorstellungen.holeVorstellung(vorstellungId);
        var gesamtbetrag = preisService.ermittlePreis(vorstellungId, zusammenhaengendePlaetze);
        return new Zahlungsanforderung(vorstellung, zusammenhaengendePlaetze, gesamtbetrag);
    }

    @Override
    public List<Kinokarte> erstelleKinokarten(VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze) {
        var vorstellung = aktuelleVorstellungen.holeVorstellung(vorstellungId);
        var kinokarten = kartenBlock.erstelleKarten(vorstellung, gewaehltePlaetze);
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        saalplan.markiereAlsVerkauft(gewaehltePlaetze);
        saalplanStapel.legeZurueck(saalplan);
        return kinokarten;
    }
}
