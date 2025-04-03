package de.wps.dddschulung.programm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "filme", schema = "programm")
@Data
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titel;
    private Integer laufzeit;
    private String posterUrl;
    private Integer fsk;
    @Lob
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
