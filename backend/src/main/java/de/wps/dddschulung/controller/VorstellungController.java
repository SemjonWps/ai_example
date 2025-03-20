package de.wps.dddschulung.controller;

import de.wps.dddschulung.model.ProgrammRepository;
import de.wps.dddschulung.model.Programmeintrag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/vorstellungen")
@CrossOrigin("*")
@RequiredArgsConstructor
public class VorstellungController {
    private final ProgrammRepository programmRepository;

    @GetMapping()
    public List<Programmeintrag> holeVorstellungenFuerTag(@RequestParam String tag) {
        LocalDate datum = LocalDate.parse(tag);
        LocalDateTime start = datum.atStartOfDay();
        LocalDateTime end = LocalTime.MAX.atDate(datum);
        return programmRepository.holeProgrammeintraegefuerZeitraum(start, end);
    }
}
