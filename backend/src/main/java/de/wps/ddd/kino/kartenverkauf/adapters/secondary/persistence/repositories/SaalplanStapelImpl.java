package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.SaalplanEntityMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Component
@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {

    private final SaalplanRepository saalplanRepository;
    private final SaalplanEntityMapper saalplanMapper;
    private final SaalRepository saalRepository;

    public Saalplan holeSaalplan(VorstellungId vorstellungId) {
        SaalplanEntity saalplanEntity = saalplanRepository.findByVorstellungUUID(vorstellungId.uuid());
        Assert.notNull(saalplanEntity, "Saalplan zu Vorstellung " + vorstellungId + " existiert nicht");
        return saalplanMapper.toDomain(saalplanEntity);
    }

    public void legeZurueck(Saalplan saalplan) {
        Integer saalplanEntityId = saalplanRepository.findIdByVorstellungUUID(saalplan.getVorstellungId().uuid()).orElse(null);
        SaalplanEntity saalplanEntity = saalplanMapper.toEntity(saalplan, saalplanEntityId);
        saalplanRepository.save(saalplanEntity);
    }

    @Override
    public void initialisiereSaalplanFuerVorstellung(Vorstellung vorstellung) {
        log.info("Erzeuge Saalplan für Vorstellung: {}", vorstellung);

        var saal = saalRepository.findByName(vorstellung.getSaal().name())
                .orElseThrow(() -> new IllegalStateException("Keine Konfiguration gefunden für Saal: " + vorstellung.getSaal().name()));

        var reihen = saal.getReihen();
        var spalten = saal.getSpalten();

        var random = new Random(42);
        var plaetze = new ArrayList<Platz>(reihen * spalten);
        for (int reihe = 1; reihe <= reihen; reihe++) {
            for (int spalte = 1; spalte <= spalten; spalte++) {
                var istVerkauft = random.nextInt(4) == 0;
                var platz = new Platz(new PlatzId(new ReiheNummer(reihe), new PlatzNummer(spalte)), istVerkauft, null);
                plaetze.add(platz);
            }
        }

        var saalplan = new Saalplan(vorstellung.getId(), plaetze);
        legeZurueck(saalplan);
    }
}
