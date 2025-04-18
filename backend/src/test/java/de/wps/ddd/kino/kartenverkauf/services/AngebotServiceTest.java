package de.wps.ddd.kino.kartenverkauf.services;

import de.wps.ddd.kino.kartenverkauf.api.mappers.PlatzDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.model.AngebotDto;
import de.wps.ddd.kino.kartenverkauf.api.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.persistence.repositories.VorstellungRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AngebotServiceTest {
    @Mock
    SaalplanStapel saalplanStapel;
    @Mock
    VorstellungRepository vorstellungRepository;
    @Mock
    Saalplan saalplan;
    @Mock
    ZusammenhaengendePlaetze zusammenhaengendePlaetze;
    @Mock
    SaalplanDtoMapper saalplanDtoMapper;
    @Mock
    PlatzDtoMapper platzDtoMapper;
    @InjectMocks
    AngebotService angebotService;
    @Mock
    SaalplanDto saalplanDto;
    String uuidString = "95b21a30-64bf-4df1-a0a2-e769bd7c5ea1";
    UUID vorstellungUUID = UUID.fromString(uuidString);

    @Test
    void holeAngebot_nichtGenugZusammenhaengendePlaetzeVorhanden_returnsAngebotFuerKeinePlaetze() {
        // arrange
        int platzanzahl = 2;
        Mockito.when(saalplanStapel.holeSaalplan(vorstellungUUID)).thenReturn(saalplan);
        Mockito.when(saalplan.sucheZusammenhaengendePlaetze(platzanzahl)).thenReturn(zusammenhaengendePlaetze);
        Mockito.when(zusammenhaengendePlaetze.plaetze()).thenReturn(List.of());
        Mockito.when(saalplanDtoMapper.saalplantoSaalplanDto(saalplan)).thenReturn(saalplanDto);

        // act
        AngebotDto angebotDto = angebotService.holeAngebot(platzanzahl, uuidString);

        // assert
        assertThat(angebotDto).isNotNull();
        assertThat(angebotDto.saalplan()).isEqualTo(saalplanDto);
        assertThat(angebotDto.angebotenePlaetze()).isEqualTo(null);
        assertThat(angebotDto.gesamtpreis()).isEqualTo(new GeldbetragDto(0, "EUR"));
    }

    @Test
    void holeAngebot_zusammenhaengendePlaetzeVorhanden_returnsAngebotZusammenhaengendePlaetze() {
        // arrange
        int platzanzahl = 2;
        Mockito.when(saalplanStapel.holeSaalplan(vorstellungUUID)).thenReturn(saalplan);
        Mockito.when(saalplan.sucheZusammenhaengendePlaetze(platzanzahl)).thenReturn(zusammenhaengendePlaetze);
        ReiheNummer reiheNr = new ReiheNummer(1);
        PlatzId platzId1 = new PlatzId(reiheNr, new PlatzNummer(11));
        PlatzId platzId2 = new PlatzId(reiheNr, new PlatzNummer(12));
        Mockito.when(zusammenhaengendePlaetze.plaetze()).thenReturn(List.of(platzId1, platzId2));
        Mockito.when(vorstellungRepository.findEintrittspreisByUuid(vorstellungUUID)).thenReturn(750);
        Mockito.when(saalplanDtoMapper.saalplantoSaalplanDto(saalplan)).thenReturn(saalplanDto);
        PlatzIdDto platzIdDto1 = new PlatzIdDto(1, 11);
        PlatzIdDto platzIdDto2 = new PlatzIdDto(1, 12);
        Mockito.when(platzDtoMapper.platzIdToPlatzIdDto(platzId1)).thenReturn(platzIdDto1);
        Mockito.when(platzDtoMapper.platzIdToPlatzIdDto(platzId2)).thenReturn(platzIdDto2);

        // act
        AngebotDto angebotDto = angebotService.holeAngebot(platzanzahl, uuidString);

        // assert
        assertThat(angebotDto).isNotNull();
        assertThat(angebotDto.saalplan()).isEqualTo(saalplanDto);
        assertThat(angebotDto.angebotenePlaetze()).containsExactly(platzIdDto1, platzIdDto2);
        assertThat(angebotDto.gesamtpreis()).isEqualTo(new GeldbetragDto(1500, "EUR"));
    }
}