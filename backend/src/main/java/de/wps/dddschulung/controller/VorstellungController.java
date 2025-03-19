package de.wps.dddschulung.controller;

import de.wps.dddschulung.model.Vorstellung;
import de.wps.dddschulung.model.VorstellungRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
