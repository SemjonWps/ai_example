package de.wps.ddd.kino.kartenverkauf.adapters.web.model;

import java.util.UUID;

public record VorstellungDto(UUID uuid, String beginn, String saal, String film) {
}
