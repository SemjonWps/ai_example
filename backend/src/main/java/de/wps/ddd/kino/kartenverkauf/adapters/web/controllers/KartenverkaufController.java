package de.wps.ddd.kino.kartenverkauf.adapters.web.controllers;

import de.wps.ddd.kino.kartenverkauf.adapters.web.mappers.KartenDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.web.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.PreisanfrageDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.ZahlungsbestaetigungDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.ZahlunsanforderungDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.application.ports.in.Kartenverkauf;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
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
class KartenverkaufController {

    private final VorstellungDtoMapper vorstellungDtoMapper;
    private final SaalplanDtoMapper saalplanDtoMapper;
    private final ZahlungDtoMapper zahlungDtoMapper;
    private final KartenDtoMapper kartenDtoMapper;

    private final Kartenverkauf kartenverkauf;

    @GetMapping("/vorstellungen/{id}")
    public VorstellungDto holeVorstellung(@PathVariable UUID id) {
        var vorstellungId = new VorstellungId(id);
        var vorstellung = kartenverkauf.holeVorstellung(vorstellungId);
        return vorstellungDtoMapper.toDto(vorstellung);
    }

    @GetMapping("/saalplaene/{id}")
    public SaalplanDto holeSaalplan(@PathVariable UUID id) {
        var vorstellungId = new VorstellungId(id);
        var saalplan = kartenverkauf.holeSaalplan(vorstellungId);
        return saalplanDtoMapper.saalplantoSaalplanDto(saalplan);
    }

    @GetMapping("/saalplaene/{id}/suche-zusammenhaengende-plaetze")
    public ZusammenhaengendePlaetzeDto sucheZusammenhaengendePlatze(@PathVariable UUID id, @RequestParam int platzanzahl) {
        var vorstellungId = new VorstellungId(id);

        var plaetze = kartenverkauf.sucheZusammenhaengendePlaetze(vorstellungId, platzanzahl);

        return saalplanDtoMapper.toDto(plaetze);
    }

    @PostMapping("/preisanfrage")
    public ZahlunsanforderungDto preisanfrage(@RequestBody PreisanfrageDto preisanfrageDto) {
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId());
        var zusammenhaengendePlaetze = saalplanDtoMapper.toDomain(preisanfrageDto.plaetze());

        var zahlungsanforderung = kartenverkauf.fordereBezahlungAn(vorstellungId, zusammenhaengendePlaetze);

        return zahlungDtoMapper.toDto(zahlungsanforderung);
    }

    @PostMapping("/kinokarten")
    public List<KinokarteDto> erstelleKinokarten(@RequestBody ZahlungsbestaetigungDto zahlunsbestaetigungDto) {
        if (!zahlunsbestaetigungDto.status().equals(Zahlungsbestaetigung.Status.BEZAHLT)) {
            throw new IllegalArgumentException("Karten wurde nicht bezahlt");
        }

        var vorstellungId = new VorstellungId(zahlunsbestaetigungDto.zahlungsanforderung().vorstellung().uuid());
        var zusammenhaengendePlaetze = saalplanDtoMapper.toDomain(zahlunsbestaetigungDto.zahlungsanforderung().plaetze());

        var kinokarten = kartenverkauf.erstelleKinokarten(vorstellungId, zusammenhaengendePlaetze);

        return kartenDtoMapper.toDto(kinokarten);
    }
}
