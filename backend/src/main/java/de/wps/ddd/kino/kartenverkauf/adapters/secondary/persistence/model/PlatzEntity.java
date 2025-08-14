package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "plaetze", schema = "kartenverkauf")
@Data
@NoArgsConstructor
public class PlatzEntity {
    @EmbeddedId
    private Id id;
    private boolean istVerkauft;
    private String reservierung;

    @Getter(AccessLevel.NONE)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("saalplanId") // <-- tells Hibernate to fill FK from parent
    private SaalplanEntity saalplan;

    public PlatzEntity(SaalplanEntity saalplan, int reihe, int platz, boolean istVerkauft, String reservierung) {
        this.saalplan = saalplan;
        this.id = new Id(saalplan.getId(), reihe, platz);
        this.istVerkauft = istVerkauft;
        this.reservierung = reservierung;
    }

    @Embeddable
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Id {
        private Integer saalplanId;
        private int reihe;
        private int platz;
    }
}
