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
public class SaalplanStapel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Saalplan> saalplaene;

    public Saalplan holeSaalplan(Vorstellung vorstellung) {
        return null;
    }

    public void legeZurueck(Saalplan saalplan) {

    }
}
