package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.controllers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.KartenDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers.ZahlungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PreisanfrageDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZahlungsstatusDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZahlungsvorgangDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.events.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Kartenverkauf;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.Zahlung;
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

    private final VorstellungDtoMapper vorstellungMapper;
    private final SaalplanDtoMapper saalplanMapper;
    private final ZahlungDtoMapper zahlungMapper;
    private final KartenDtoMapper kartenMapper;

    private final Kartenverkauf kartenverkauf;
    private final Zahlung zahlung;

    @GetMapping("/vorstellungen/{id}")
    public VorstellungDto holeVorstellung(@PathVariable UUID id) {
        var vorstellungId = new VorstellungId(id);
        var vorstellung = kartenverkauf.holeVorstellung(vorstellungId);
        return vorstellungMapper.toDto(vorstellung);
    }

    @GetMapping("/saalplaene/{id}")
    public SaalplanDto holeSaalplan(@PathVariable UUID id) {
        var vorstellungId = new VorstellungId(id);
        var saalplan = kartenverkauf.holeSaalplan(vorstellungId);
        return saalplanMapper.toDto(saalplan);
    }

    @GetMapping("/saalplaene/{id}/suche-zusammenhaengende-plaetze")
    public ZusammenhaengendePlaetzeDto sucheZusammenhaengendePlatze(@PathVariable UUID id, @RequestParam int platzanzahl) {
        var vorstellungId = new VorstellungId(id);
        var anzahl = new Platzanzahl(platzanzahl);

        var plaetze = kartenverkauf.sucheZusammenhaengendePlaetze(vorstellungId, anzahl);

        return saalplanMapper.toDto(plaetze);
    }

    @PostMapping("/preisanfrage")
    public GeldbetragDto preisanfrage(@RequestBody PreisanfrageDto preisanfrageDto) {
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId());
        var gewaehltePlaetze = saalplanMapper.toDomain(preisanfrageDto.plaetze());

        var gesamtpreis = kartenverkauf.berechneGesamtpreis(vorstellungId, gewaehltePlaetze);

        return zahlungMapper.toDto(gesamtpreis);
    }

    @PostMapping("/zahlung")
    public ZahlungsvorgangDto starteZahlungsvorgang(@RequestBody PreisanfrageDto preisanfrageDto) {
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId());
        var gewaehltePlaetze = saalplanMapper.toDomain(preisanfrageDto.plaetze());

        var gesamtpreis = kartenverkauf.berechneGesamtpreis(vorstellungId, gewaehltePlaetze);
        var auftragsnummer = zahlung.starteZahlungsvorgang(gesamtpreis, vorstellungId, gewaehltePlaetze);

        return new ZahlungsvorgangDto(
                auftragsnummer.nummer().toString(),
                preisanfrageDto.vorstellungId().toString(),
                preisanfrageDto.plaetze(),
                zahlungMapper.toDto(gesamtpreis));
    }

    @GetMapping("/zahlung/{id}/status")
    public ZahlungsstatusDto zahlungStatus(@PathVariable UUID id) {
        var auftragsnummer = new Auftragsnummer(id);
        var status = zahlung.status(auftragsnummer);
        return zahlungMapper.toDto(status);
    }

    // In Wirklichkeit würde diese Bestätigung vom externen Zahlungsdienstleister kommen, nicht von der UI
    @PostMapping("/zahlung/{id}/bestaetigen")
    public ZahlungsstatusDto bestaetigeZahlungseingang(@PathVariable UUID id) {
        var auftragsnummer = new Auftragsnummer(id);
        zahlung.verarbeite(new ZahlungEingegangen(auftragsnummer));
        return zahlungMapper.toDto(zahlung.status(auftragsnummer));
    }

    @PostMapping("/kinokarten/{id}")
    public List<KinokarteDto> erstelleKinokarten(@PathVariable UUID id, @RequestBody PreisanfrageDto preisanfrageDto) {
        var auftragsnummer = new Auftragsnummer(id);
        // TODO sollte aus Auftragsnummer hervorgehen. get statt post request
        var vorstellungId = new VorstellungId(preisanfrageDto.vorstellungId());
        var gewaehltePlaetze = saalplanMapper.toDomain(preisanfrageDto.plaetze());

        var kinokarten = kartenverkauf.erstelleKinokarten(auftragsnummer, vorstellungId, gewaehltePlaetze);

        return kartenMapper.toDto(kinokarten);
    }
}
