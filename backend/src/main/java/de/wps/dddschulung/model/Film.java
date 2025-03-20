package de.wps.dddschulung.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "filme")
@Data
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titel;
    private Duration laufzeit;
    private String posterUrl;
    private Integer fsk;
    private String beschreibung;
    private String genre;
    private String hauptdarsteller;
    private String regie;
    private String sprache;
    @OneToMany
    @JoinColumn(name = "filmId")
    @JsonIgnore
    private List<Vorstellung> vorstellungen;

}
