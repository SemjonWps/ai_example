package de.wps.ddd.kino.kartenverkauf.api;

import de.wps.ddd.kino.kartenverkauf.domain.Angebot;
import de.wps.ddd.kino.kartenverkauf.services.AngebotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class KartenverkaufController {

    private final AngebotService angebotService;

    @GetMapping()
    public Angebot holeAngebot(@RequestParam int platzanzahl, @RequestParam String vorstellungUuid) {
        return angebotService.holeAngebot(platzanzahl, vorstellungUuid);
    }
}
