package de.wps.dddschulung.kartenverkauf.api.mappers;

import de.wps.dddschulung.kartenverkauf.api.model.PlatzDto;
import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzMapperTest {
    PlatzMapper platzMapper = new PlatzMapperImpl();


    @Test
    void testPlatzToPlatzDto() {
        // arrange
        long id = 1L;
        Sitz sitz = new Sitz(2);
        Reihe reihe = new Reihe(3);
        boolean istVerkauft = false;
        long saalId = 4L;
        Platz platz = new Platz(id, sitz, reihe, istVerkauft, null, saalId);

        // act
        PlatzDto platzDto = platzMapper.platzToPlatzDto(platz);

        // assert
        assertThat(platzDto.sitz()).isEqualTo(sitz);
        assertThat(platzDto.reihe()).isEqualTo(reihe);
    }
}