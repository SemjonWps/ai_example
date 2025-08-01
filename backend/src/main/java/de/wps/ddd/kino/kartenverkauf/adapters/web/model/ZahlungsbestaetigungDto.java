package de.wps.ddd.kino.kartenverkauf.adapters.web.model;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsbestaetigung;

public record ZahlungsbestaetigungDto(
        ZahlunsanforderungDto zahlungsanforderung,
        Zahlungsbestaetigung.Status status
) {
}
