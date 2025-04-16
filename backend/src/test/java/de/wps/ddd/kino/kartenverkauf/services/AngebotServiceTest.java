package de.wps.ddd.kino.kartenverkauf.services;

import de.wps.ddd.kino.kartenverkauf.api.mappers.PlatzDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.mappers.SaalplanDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.model.AngebotDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
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
        assertThat(angebotDto.saalplanDto()).isEqualTo(saalplanDto);
        assertThat(angebotDto.platzDtos()).isEqualTo(null);
        assertThat(angebotDto.gesamtpreis()).isEqualTo(new Geldbetrag(0));
    }

    @Test
    void holeAngebot_zusammenhaengendePlaetzeVorhanden_returnsAngebotZusammenhaengendePlaetze() {
        // arrange
        int platzanzahl = 2;
        Mockito.when(saalplanStapel.holeSaalplan(vorstellungUUID)).thenReturn(saalplan);
        Mockito.when(saalplan.sucheZusammenhaengendePlaetze(platzanzahl)).thenReturn(zusammenhaengendePlaetze);
        Reihennummer reihennummer = new Reihennummer(1);
        Platz platz1 = new Platz(1L, new Platznummer(11), reihennummer, false, null, 5L);
        Platz platz2 = new Platz(2L, new Platznummer(12), reihennummer, false, null, 5L);
        Mockito.when(zusammenhaengendePlaetze.plaetze()).thenReturn(List.of(platz1, platz2));
        Mockito.when(vorstellungRepository.findEintrittspreisByUuid(vorstellungUUID)).thenReturn(750);
        Mockito.when(saalplanDtoMapper.saalplantoSaalplanDto(saalplan)).thenReturn(saalplanDto);
        PlatzDto platzDto1 = new PlatzDto(1, 11, SitzplatzStatus.FREI);
        PlatzDto platzDto2 = new PlatzDto(1, 12, SitzplatzStatus.FREI);
        Mockito.when(platzDtoMapper.platzToPlatzDto(platz1)).thenReturn(platzDto1);
        Mockito.when(platzDtoMapper.platzToPlatzDto(platz2)).thenReturn(platzDto2);

        // act
        AngebotDto angebotDto = angebotService.holeAngebot(platzanzahl, uuidString);

        // assert
        assertThat(angebotDto).isNotNull();
        assertThat(angebotDto.saalplanDto()).isEqualTo(saalplanDto);
        assertThat(angebotDto.platzDtos()).containsExactly(platzDto1, platzDto2);
        assertThat(angebotDto.gesamtpreis()).isEqualTo(new Geldbetrag(1500));
    }
}