package de.wps.ddd.kino.kartenverkauf.api;

import de.wps.ddd.kino.kartenverkauf.api.model.AngebotDto;
import de.wps.ddd.kino.kartenverkauf.services.AngebotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class KartenverkaufController {

    private final AngebotService angebotService;

    @GetMapping()
    public AngebotDto holeAngebot(@RequestParam int platzanzahl, @RequestParam String vorstellungUuid) {
        return angebotService.holeAngebot(platzanzahl, vorstellungUuid);
    }
}
