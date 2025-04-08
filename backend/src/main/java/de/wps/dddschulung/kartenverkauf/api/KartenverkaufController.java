package de.wps.dddschulung.kartenverkauf.api;

import de.wps.dddschulung.kartenverkauf.domain.Angebot;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin("*")
@RequiredArgsConstructor
public class KartenverkaufController {

    private final SaalplanStapel saalplanStapel;

    @GetMapping()
    public Angebot holeAngebot(@RequestParam int platzanzahl, @RequestParam String vorstellungUuid) {
        Saalplan saalplan = saalplanStapel.holeSaalplan(UUID.fromString(vorstellungUuid));


    }
}
