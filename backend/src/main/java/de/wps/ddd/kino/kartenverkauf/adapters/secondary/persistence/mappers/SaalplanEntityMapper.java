package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SaalplanEntityMapper {

    public SaalplanEntity toEntity(Saalplan saalplan, @Nullable Integer saalplanId) {
        var saalplanEntity = new SaalplanEntity(saalplanId, saalplan.getVorstellungId().uuid());
        saalplan.getPlaetze().values().stream() // this code raises an exception during debugging but actually runs fine. The issue is probably some Hibernate circular reference magic problem.
                .flatMap(inner -> inner.values().stream())
                .forEach(platz -> saalplanEntity.addPlatz(
                        platz.getId().reihe().nummer(),
                        platz.getId().platz().nummer(),
                        platz.isIstVerkauft(),
                        platz.getReservierung() != null ? platz.getReservierung().nummer() : null
                ));
        return saalplanEntity;
    }

    public Saalplan toDomain(SaalplanEntity saalplanEntity) {
        return new Saalplan(
                new VorstellungId(saalplanEntity.getVorstellungUUID()),
                saalplanEntity.getPlaetze().stream().map(
                        p -> new Platz(
                                new PlatzId(
                                        new ReiheNummer(p.getId().getReihe()),
                                        new PlatzNummer(p.getId().getPlatz())),
                                p.isIstVerkauft(),
                                p.getReservierung() != null ? new Reservierungsnummer(p.getReservierung()) : null
                        )
                ).toList());
    }
}
