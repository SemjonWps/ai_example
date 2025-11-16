package de.wps.ddd.kino.filmauswahl.web;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilmEingebenDto {
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
