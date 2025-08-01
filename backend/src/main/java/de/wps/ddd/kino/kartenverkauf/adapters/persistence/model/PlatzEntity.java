package de.wps.ddd.kino.kartenverkauf.adapters.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
    @EmbeddedId
    private Id id;
    private boolean istVerkauft;
    private String reservierung;

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        @Column(name = "saalplan_id")
        private int saalplanId;
        private int reihe;
        private int platz;
    }
}
