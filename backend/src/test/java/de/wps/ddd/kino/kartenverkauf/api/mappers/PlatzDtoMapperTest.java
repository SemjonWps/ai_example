package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlatzDtoMapperTest {

    PlatzDtoMapper platzDtoMapper = new PlatzDtoMapperImpl();

    private final ReiheNummer reiheNr = new ReiheNummer(3);
    private final PlatzNummer platzNr = new PlatzNummer(2);
    private final PlatzId platzId = new PlatzId(reiheNr, platzNr);

    @Test
    void testPlatzToPlatzDto_nichtVerkauftNichtReserviert() {
        // arrange
        Platz platz = new Platz(platzId, false, null);

        // act
        PlatzDto platzDto = platzDtoMapper.toDto(platz);

        // assert
        assertThat(platzDto.reiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.platzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzDto.istFrei()).isTrue();
    }

    @Test
    void testPlatzToPlatzDto_verkauft() {
        // arrange
        Platz platz = new Platz(platzId, true, null);

        // act
        PlatzDto platzDto = platzDtoMapper.toDto(platz);

        // assert
        assertThat(platzDto.reiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.platzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzDto.istFrei()).isFalse();
    }

    @Test
    void testPlatzToPlatzDto_reserviertNichtVerkauft() {
        // arrange
        Platz platz = new Platz(platzId, false, new Reservierungsnummer("reservierungsnummer"));

        // act
        PlatzDto platzDto = platzDtoMapper.toDto(platz);

        // assert
        assertThat(platzDto.reiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.platzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzDto.istFrei()).isFalse();
    }
}