package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlatzDtoMapperTest {
    PlatzDtoMapper platzDtoMapper = new PlatzDtoMapperImpl();

    private final long id = 1L;
    private final Platznummer platznummer = new Platznummer(2);
    private final Reihennummer reihennummer = new Reihennummer(3);
    private final PlatzId platzId = new PlatzId(reihennummer, platznummer);
    private final long saalId = 4L;

    @Test
    void testPlatzToPlatzDto_nichtVerkauftNichtReserviert() {
        // arrange
        boolean istVerkauft = false;
        Platz platz = new Platz(id, platzId, istVerkauft, null, saalId);

        // act
        PlatzDto platzDto = platzDtoMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.platznummer()).isEqualTo(platznummer.nummer());
        assertThat(platzDto.reihennummer()).isEqualTo(reihennummer.nummer());
        assertThat(platzDto.sitzplatzStatus()).isEqualTo(SitzplatzStatus.FREI);
    }

    @Test
    void testPlatzToPlatzDto_verkauft() {
        // arrange
        boolean istVerkauft = true;
        Platz platz = new Platz(id, platzId, istVerkauft, null, saalId);

        // act
        PlatzDto platzDto = platzDtoMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.platznummer()).isEqualTo(platznummer.nummer());
        assertThat(platzDto.reihennummer()).isEqualTo(reihennummer.nummer());
        assertThat(platzDto.sitzplatzStatus()).isEqualTo(SitzplatzStatus.BELEGT);
    }

    @Test
    void testPlatzToPlatzDto_reserviertNichtVerkauft() {
        // arrange
        boolean istVerkauft = false;
        Platz platz = new Platz(id, platzId, istVerkauft, new Reservierungsnummer("reservierungsnummer"), saalId);

        // act
        PlatzDto platzDto = platzDtoMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.platznummer()).isEqualTo(platznummer.nummer());
        assertThat(platzDto.reihennummer()).isEqualTo(reihennummer.nummer());
        assertThat(platzDto.sitzplatzStatus()).isEqualTo(SitzplatzStatus.BELEGT);
    }
}