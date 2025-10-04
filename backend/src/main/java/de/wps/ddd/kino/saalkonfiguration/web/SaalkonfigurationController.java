package de.wps.ddd.kino.saalkonfiguration.web;

import de.wps.ddd.kino.saalkonfiguration.data.SaalConfiguration;
import de.wps.ddd.kino.saalkonfiguration.service.SaalkonfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/saalkonfiguration")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SaalkonfigurationController {

    private final SaalkonfigurationService saalkonfigurationService;

    @GetMapping()
    public List<SaalConfiguration> alleSaele() {
        return saalkonfigurationService.alleSaele();
    }

    @PostMapping()
    public SaalConfiguration createSaal(@RequestBody SaalConfiguration saal) {
        return saalkonfigurationService.saveSaal(saal);
    }

    @PutMapping()
    public SaalConfiguration updateSaal(@RequestBody SaalConfiguration saal) {
        return saalkonfigurationService.saveSaal(saal);
    }

    @DeleteMapping("/{name}")
    public void deleteSaal(@PathVariable String name) {
        saalkonfigurationService.deleteSaal(name);
    }
}
