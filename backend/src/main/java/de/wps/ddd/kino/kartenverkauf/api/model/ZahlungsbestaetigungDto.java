package de.wps.ddd.kino.kartenverkauf.api.model;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsbestaetigung;

public record ZahlungsbestaetigungDto(
        ZahlunsanforderungDto zahlungsanforderung,
        Zahlungsbestaetigung.Status status
) {
}
