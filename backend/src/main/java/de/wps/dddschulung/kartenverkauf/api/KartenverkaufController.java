package de.wps.dddschulung.kartenverkauf.api;

import de.wps.dddschulung.kartenverkauf.domain.Angebot;
import de.wps.dddschulung.kartenverkauf.services.AngebotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin("*")
@RequiredArgsConstructor
public class KartenverkaufController {

    private final AngebotService angebotService;

    @GetMapping()
    public Angebot holeAngebot(@RequestParam int platzanzahl, @RequestParam String vorstellungUuid) {
        System.out.println("------------");
        return angebotService.holeAngebot(platzanzahl, vorstellungUuid);
    }
}
