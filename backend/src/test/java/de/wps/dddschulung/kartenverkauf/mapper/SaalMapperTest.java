package de.wps.dddschulung.kartenverkauf.mapper;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Saal;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SaalMapperTest {
    private final SaalMapper saalMapper = new SaalMapperImpl();

    private final long saalId = 1L;
    private final String saalName = "Großer Saal";
    Saal saal = new Saal(saalId, saalName);

    @Test
    public void mapSaalToSaalEntity() {
        // act
        SaalEntity saalEntity = saalMapper.SaalToSaalEntity(saal);

        // assert
        assertThat(saalEntity.getId()).isEqualTo(saalId);
        assertThat(saalEntity.getName()).isEqualTo(saalName);
    }
}