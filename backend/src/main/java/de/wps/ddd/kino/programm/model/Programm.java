package de.wps.ddd.kino.programm.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Programm {
    private LocalDateTime von;
    private LocalDateTime bis;
    private List<Programmeintrag> programmeintraege;
}
