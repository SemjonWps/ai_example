package de.wps.ddd.kino.saalkonfiguration.web;

import de.wps.ddd.kino.saalkonfiguration.data.SaalKonfiguration;
import de.wps.ddd.kino.saalkonfiguration.service.SaalKonfigurationService;
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
public class SaalKonfigurationController {

    private final SaalKonfigurationService saalkonfigurationService;

    @GetMapping()
    public List<SaalKonfiguration> alleSaele() {
        return saalkonfigurationService.alleSaele();
    }

    @PostMapping()
    public SaalKonfiguration createSaal(@RequestBody SaalKonfiguration saal) {
        return saalkonfigurationService.saveSaal(saal);
    }

    @PutMapping()
    public SaalKonfiguration updateSaal(@RequestBody SaalKonfiguration saal) {
        return saalkonfigurationService.saveSaal(saal);
    }

    @DeleteMapping("/{name}")
    public void deleteSaal(@PathVariable String name) {
        saalkonfigurationService.deleteSaal(name);
    }
}
