package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlatzDtoMapperTest {
    PlatzDtoMapper platzDtoMapper = new PlatzDtoMapperImpl();

    private final long id = 1L;
    private final PlatzNummer platzNr = new PlatzNummer(2);
    private final ReiheNummer reiheNr = new ReiheNummer(3);
    private final PlatzId platzId = new PlatzId(reiheNr, platzNr);

    @Test
    void testPlatzToPlatzDto_nichtVerkauftNichtReserviert() {
        // arrange
        boolean istVerkauft = false;
        Platz platz = new Platz(id, platzId, istVerkauft, null);

        // act
        PlatzDto platzDto = platzDtoMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.platzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzDto.reiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.sitzplatzStatus()).isEqualTo(SitzplatzStatus.FREI);
    }

    @Test
    void testPlatzToPlatzDto_verkauft() {
        // arrange
        boolean istVerkauft = true;
        Platz platz = new Platz(id, platzId, istVerkauft, null);

        // act
        PlatzDto platzDto = platzDtoMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.platzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzDto.reiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.sitzplatzStatus()).isEqualTo(SitzplatzStatus.BELEGT);
    }

    @Test
    void testPlatzToPlatzDto_reserviertNichtVerkauft() {
        // arrange
        boolean istVerkauft = false;
        Platz platz = new Platz(id, platzId, istVerkauft, new Reservierungsnummer("reservierungsnummer"));

        // act
        PlatzDto platzDto = platzDtoMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.platzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzDto.reiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzDto.sitzplatzStatus()).isEqualTo(SitzplatzStatus.BELEGT);
    }
}