package de.wps.ddd.kino.kartenverkauf.api;

import de.wps.ddd.kino.kartenverkauf.api.mappers.KartenDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.PlatzDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PreisanfrageDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.api.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.api.model.ZahlungsbestaetigungDto;
import de.wps.ddd.kino.kartenverkauf.api.model.ZahlunsanforderungDto;
import de.wps.ddd.kino.kartenverkauf.api.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.domain.factories.KartenBlock;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.services.PreisService;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsbestaetigung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/kartenverkauf")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class KartenverkaufController {

    private final Vorstellungen vorstellungen;
    private final VorstellungDtoMapper vorstellungDtoMapper;

    private final SaalplanStapel saalplanStapel;
    private final SaalplanDtoMapper saalplanDtoMapper;
    private final PlatzDtoMapper platzDtoMapper;

    private final PreisService preisService;
    private final ZahlungDtoMapper zahlungDtoMapper;

    private final KartenBlock kartenBlock;
    private final KartenDtoMapper kartenDtoMapper;

    @GetMapping("/vorstellungen/{id}")
    public VorstellungDto holeVorstellung(@PathVariable UUID id) {
        var vorstellung = vorstellungen.holeVorstellung(id);
        return vorstellungDtoMapper.toDto(vorstellung);
    }

    @GetMapping("/saalplaene/{id}")
    public SaalplanDto holeSaalplan(@PathVariable UUID id) {
        var saalplan = saalplanStapel.holeSaalplan(id);
        return saalplanDtoMapper.saalplantoSaalplanDto(saalplan);
    }

    @GetMapping("/saalplaene/{id}/suche-zusammenhaengende-plaetze")
    public ZusammenhaengendePlaetzeDto sucheZusammenhaengendePlatze(@PathVariable UUID id, @RequestParam int platzanzahl) {
        var saalplan = saalplanStapel.holeSaalplan(id);
        var plaetze = saalplan.sucheZusammenhaengendePlaetze(platzanzahl);
        return platzDtoMapper.toDto(plaetze);
    }

    @PostMapping("/preisanfrage")
    public ZahlunsanforderungDto preisanfrage(@RequestBody PreisanfrageDto preisanfrageDto) {
        var vorstellung = vorstellungen.holeVorstellung(preisanfrageDto.vorstellungUuid());
        var zusammenhaengendePlaetze = platzDtoMapper.toDomain(preisanfrageDto.plaetze());
        var gesamtbetrag = preisService.ermittlePreis(preisanfrageDto.vorstellungUuid(), zusammenhaengendePlaetze);
        var zahlungsanforderung = new Zahlungsanforderung(vorstellung, zusammenhaengendePlaetze, gesamtbetrag);
        return zahlungDtoMapper.toDto(zahlungsanforderung);
    }

    @PostMapping("/kinokarten")
    public List<KinokarteDto> erstelleKinokarten(@RequestBody ZahlungsbestaetigungDto zahlunsbestaetigungDto) {
        if (!zahlunsbestaetigungDto.status().equals(Zahlungsbestaetigung.Status.BEZAHLT)) {
            throw new IllegalArgumentException("Karten wurde nicht bezahlt");
        }

        var vorstellungUuid = UUID.fromString(zahlunsbestaetigungDto.zahlungsanforderung().vorstellung().uuid());
        var vorstellung = vorstellungen.holeVorstellung(vorstellungUuid);
        var zusammenhaengendePlaetze = platzDtoMapper.toDomain(zahlunsbestaetigungDto.zahlungsanforderung().plaetze());
        var kinokarten = kartenBlock.erstelleKarten(vorstellung, zusammenhaengendePlaetze);
        var saalplan = saalplanStapel.holeSaalplan(vorstellungUuid);
        saalplan.markiereAlsVerkauft(zusammenhaengendePlaetze);

        return kartenDtoMapper.toDto(kinokarten);
    }
}
