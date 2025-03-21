package de.wps.dddschulung.programm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Programmeintrag {
    private Film film;
    private List<Vorstellung> vorstellungen;
}
