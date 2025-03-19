package de.wps.dddschulung.controller;

import de.wps.dddschulung.model.Vorstellung;
import de.wps.dddschulung.model.VorstellungRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/vorstellungen")
@CrossOrigin("*")
public class VorstellungController {
    private final VorstellungRepository vorstellungRepository;

    public VorstellungController(VorstellungRepository vorstellungRepository) {
        this.vorstellungRepository = vorstellungRepository;
    }

    @GetMapping
    public List<Vorstellung> getAllVorstellungen(){
        return vorstellungRepository.findAllByOrderByAnfangszeitAsc();
    }

    @GetMapping("/tag")
    public List<Vorstellung> getTagesvorstellungen(@RequestParam String datumString){
        LocalDate datum = LocalDate.parse(datumString);
        LocalDateTime start = datum.atStartOfDay();
        LocalDateTime end = LocalTime.MAX.atDate(datum);
        return vorstellungRepository.findByAnfangszeitBetween(start, end);
    }
}
