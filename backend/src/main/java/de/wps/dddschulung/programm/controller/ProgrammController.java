package de.wps.dddschulung.programm.controller;

import de.wps.dddschulung.programm.model.Programm;
import de.wps.dddschulung.programm.model.ProgrammRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/programm")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ProgrammController {

    private final ProgrammRepository programmRepository;

    @GetMapping()
    public Programm holeProgrammFuerTag(@RequestParam LocalDate datum) {
        return programmRepository.holeProgrammFuerTag(datum);
    }
}
