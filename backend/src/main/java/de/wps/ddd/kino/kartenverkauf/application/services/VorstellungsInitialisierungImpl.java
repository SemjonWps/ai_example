package de.wps.ddd.kino.kartenverkauf.application.services;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.VorstellungsInitialisierung;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class VorstellungsInitialisierungImpl implements VorstellungsInitialisierung {

    private final SaalplanStapel saalplanStapel;

    @Override
    public void initialisiereSaalplan(Vorstellung vorstellung) {
        log.info("Initialisiere Saalplan für Vorstellung: {}", vorstellung);
        saalplanStapel.initialisiereSaalplanFuerVorstellung(vorstellung);
    }
}
