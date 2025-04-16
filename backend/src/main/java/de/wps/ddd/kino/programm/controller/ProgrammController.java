package de.wps.ddd.kino.programm.controller;

import de.wps.ddd.kino.programm.model.Programm;
import de.wps.ddd.kino.programm.model.ProgrammRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
