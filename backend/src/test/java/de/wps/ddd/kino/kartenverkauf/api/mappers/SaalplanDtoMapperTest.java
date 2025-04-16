package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class SaalplanDtoMapperTest {
    @Mock
    PlatzDtoMapper platzDtoMapper;
    @InjectMocks
    SaalplanDtoMapper mapper;

    @Test
    void saalplantoSaalplanDto() {
        // arrange
        List<Platz> platzListe = new ArrayList<>();

        Reihennummer reihennummer1 = new Reihennummer(1);
        Reihennummer reihennummer2 = new Reihennummer(2);

        Platznummer platznummer1 = new Platznummer(1);
        Platznummer platznummer2 = new Platznummer(2);

        Platz reihe1_platz1 = new Platz(null, platznummer1, reihennummer1, false, null, null);
        Platz rehie1_platz2 = new Platz(null, platznummer2, reihennummer1, true, null, null);
        Platz reihe2_platz1 = new Platz(null, platznummer1, reihennummer2, true, null, null);
        Platz reihe2_platz2 = new Platz(null, platznummer2, reihennummer2, false, new Reservierungsnummer("reservierungsnummer"), null);

        platzListe.add(reihe1_platz1);
        platzListe.add(rehie1_platz2);
        platzListe.add(reihe2_platz1);
        platzListe.add(reihe2_platz2);

        PlatzDto reihe1_platz1_dto = new PlatzDto(1, 1, SitzplatzStatus.BELEGT);
        PlatzDto reihe1_platz2_dto = new PlatzDto(1, 1, SitzplatzStatus.FREI);
        PlatzDto reihe2_platz1_dto = new PlatzDto(2, 1, SitzplatzStatus.FREI);
        PlatzDto reihe2_platz2_dto = new PlatzDto(2, 1, SitzplatzStatus.BELEGT);

        Mockito.when(platzDtoMapper.platzToPlatzDto(reihe1_platz1)).thenReturn(reihe1_platz1_dto);
        Mockito.when(platzDtoMapper.platzToPlatzDto(rehie1_platz2)).thenReturn(reihe1_platz2_dto);
        Mockito.when(platzDtoMapper.platzToPlatzDto(reihe2_platz1)).thenReturn(reihe2_platz1_dto);
        Mockito.when(platzDtoMapper.platzToPlatzDto(reihe2_platz2)).thenReturn(reihe2_platz2_dto);

        Saalplan saalplan = new Saalplan(null, null, platzListe);
        PlatzDto[][] expectedPlatzDtos = {
                {reihe1_platz1_dto, reihe1_platz2_dto},
                {reihe2_platz1_dto, reihe2_platz2_dto},
        };

        // act
        SaalplanDto saalplanDto = mapper.saalplantoSaalplanDto(saalplan);

        // assert
        assertThat(saalplanDto.platzbelegungen()).isEqualTo(expectedPlatzDtos);
    }
}
