package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "plaetze")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatzEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int platznummer;
    private int reihennummer;
    @Getter
    @Accessors(fluent = true)
    private boolean istVerkauft;
    private String reservierungsnummer;
}
