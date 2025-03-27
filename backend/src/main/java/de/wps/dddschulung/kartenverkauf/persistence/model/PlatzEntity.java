package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatzEntity {
    @Id
    private Long id;
    private int platznummer;
    private int reihe;
    private boolean belegt = false;
    private String reservierungsnummer = null;

}
