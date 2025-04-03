package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "plaetze", schema = "kartenverkauf")
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
    private long saalplan_id;
}
