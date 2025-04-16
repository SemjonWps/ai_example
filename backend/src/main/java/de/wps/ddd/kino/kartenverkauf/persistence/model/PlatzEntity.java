package de.wps.ddd.kino.kartenverkauf.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean istVerkauft;
    private String reservierungsnummer;
    private long saalplan_id;
}
