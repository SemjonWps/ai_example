package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.common.architecture.ApplicationService;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;

@ApplicationService
public interface VorstellungsInitialisierung {

    /**
     * Initialisiert den Saalplan für eine Vorstellung
     *
     * @param vorstellung Die Vorstellung, für die der Saalplan initialisiert werden soll
     */
    void initialisiereSaalplan(Vorstellung vorstellung);
}
