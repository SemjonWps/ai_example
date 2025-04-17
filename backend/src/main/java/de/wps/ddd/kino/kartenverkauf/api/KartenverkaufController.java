package de.wps.ddd.kino.kartenverkauf.api;

import de.wps.ddd.kino.kartenverkauf.api.model.AngebotDto;
import de.wps.ddd.kino.kartenverkauf.api.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.api.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.api.model.ZahlungErfolgtDto;
import de.wps.ddd.kino.kartenverkauf.services.AngebotService;
import de.wps.ddd.kino.kartenverkauf.services.VorstellungService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class KartenverkaufController {

    private final AngebotService angebotService;
    private final VorstellungService vorstellungService;

    @GetMapping()
    public AngebotDto holeAngebot(@RequestParam int platzanzahl, @RequestParam String vorstellungUuid) {
        return angebotService.holeAngebot(platzanzahl, vorstellungUuid);
    }

    @GetMapping("/vorstellung")
    public VorstellungDto holeAngebot(@RequestParam String vorstellungUuid) {
        return vorstellungService.holeVorstellung(vorstellungUuid);
    }

    @PostMapping("/kinokarten")
    public KinokarteDto[] holeEintrittskarten(@RequestBody ZahlungErfolgtDto zahlungErfolgtDto) {
        return new KinokarteDto[2];
    }
}
