package de.wps.dddschulung.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="filme")
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
    private String beschreibung;
    private String genre;
    private String hauptdarsteller;
    private String regie;
    private String sprache;

}
