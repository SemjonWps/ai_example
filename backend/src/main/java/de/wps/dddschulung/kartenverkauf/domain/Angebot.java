package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.api.model.PlatzDto;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
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
