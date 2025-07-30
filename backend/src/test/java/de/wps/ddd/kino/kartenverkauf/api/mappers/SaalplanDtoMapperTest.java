package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
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
class SaalplanDtoMapperTest {

    @Mock
    PlatzDtoMapper platzDtoMapper;
    @InjectMocks
    SaalplanDtoMapper mapper;

    @Test
    void saalplantoSaalplanDto() {
        // arrange

        var reiheNr1 = new ReiheNummer(1);
        var reiheNr2 = new ReiheNummer(2);

        var platzNr1 = new PlatzNummer(1);
        var platzNr2 = new PlatzNummer(2);

        var platzId1 = new PlatzId(reiheNr1, platzNr1);
        var platzId2 = new PlatzId(reiheNr1, platzNr2);
        var platzId3 = new PlatzId(reiheNr2, platzNr1);
        var platzId4 = new PlatzId(reiheNr2, platzNr2);

        var reihe1_platz1 = new Platz(platzId1, false, null);
        var reihe1_platz2 = new Platz(platzId2, true, null);
        var reihe2_platz1 = new Platz(platzId3, true, null);
        var reihe2_platz2 = new Platz(platzId4, false, new Reservierungsnummer("reservierungsnummer"));

        var plaetze = List.of(reihe1_platz1, reihe1_platz2, reihe2_platz1, reihe2_platz2);

        var reihe1_platz1_dto = new PlatzDto(1, 1, false);
        var reihe1_platz2_dto = new PlatzDto(1, 1, true);
        var reihe2_platz1_dto = new PlatzDto(2, 1, true);
        var reihe2_platz2_dto = new PlatzDto(2, 1, false);

        Mockito.when(platzDtoMapper.toDto(reihe1_platz1)).thenReturn(reihe1_platz1_dto);
        Mockito.when(platzDtoMapper.toDto(reihe1_platz2)).thenReturn(reihe1_platz2_dto);
        Mockito.when(platzDtoMapper.toDto(reihe2_platz1)).thenReturn(reihe2_platz1_dto);
        Mockito.when(platzDtoMapper.toDto(reihe2_platz2)).thenReturn(reihe2_platz2_dto);

        var vorstellungId = new VorstellungId(UUID.randomUUID());
        var saalplan = new Saalplan(vorstellungId, plaetze);
        PlatzDto[][] expectedPlatzDtos = {
                {reihe1_platz1_dto, reihe1_platz2_dto},
                {reihe2_platz1_dto, reihe2_platz2_dto},
        };

        // act
        var saalplanDto = mapper.saalplantoSaalplanDto(saalplan);

        // assert
        assertThat(saalplanDto.plaetze()).isEqualTo(expectedPlatzDtos);
    }
}
