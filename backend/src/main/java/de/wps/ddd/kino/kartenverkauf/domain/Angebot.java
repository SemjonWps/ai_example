package de.wps.ddd.kino.kartenverkauf.domain;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Angebot {
    private Geldbetrag gesamtpreis;
    private Platzbelegungen platzbelegungen;
    private List<PlatzDto> platzDtos;
}
