package de.wps.dddschulung.kartenverkauf.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Platz {
    @Id
    private Long id;
    private int platznummer;
    private boolean belegt = false;
    private String reservierungsnummer = null;

    public void markiereAlsVerkauft() {
        belegt = true;
    }
}
