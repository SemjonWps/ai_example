package de.wps.ddd.kino.filmauswahl.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Programmeintrag {
    private Film film;
    private List<Vorstellung> vorstellungen;
}
