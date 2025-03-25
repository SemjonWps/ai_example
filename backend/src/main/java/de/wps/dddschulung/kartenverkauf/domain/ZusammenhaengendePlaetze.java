package de.wps.dddschulung.kartenverkauf.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ZusammenhaengendePlaetze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    List<Platz> plaetze;

    public void markiereAlsVerkauft() {
        for (Platz platz : ZusammenhaengendePlaetze.this.plaetze) {
            platz.markiereAlsVerkauft();
        }
    }
}
