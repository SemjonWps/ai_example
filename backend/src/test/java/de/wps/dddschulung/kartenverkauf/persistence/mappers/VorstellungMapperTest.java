package de.wps.dddschulung.kartenverkauf.persistence.mappers;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Saal;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import de.wps.dddschulung.kartenverkauf.persistence.model.VorstellungEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VorstellungMapperTest {
    private final VorstellungMapper vorstellungMapper = new VorstellungMapperImpl();

    private final UUID vorstellungUUID = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private final long vorstellungId = 2L;
    private final LocalDateTime anfangszeit = LocalDateTime.of(2020, 1, 1, 0, 0);
    private final Beginn beginn = new Beginn(anfangszeit);
    private final Filmname filmname = new Filmname("Back to the Futura");
    private final String saalName = "Großer Saal";
    private final Saal saal = new Saal(saalName);

    @Test
    public void vorstellungToVorstellungEntity() {
        // arrange
        Vorstellung vorstellung = new Vorstellung(vorstellungUUID, saal, beginn, filmname);

        // act
        VorstellungEntity vorstellungEntity = vorstellungMapper.vorstellungToVorstellungEntity(vorstellung);

        // assert
        assertThat(vorstellungEntity.getUuid()).isEqualTo(vorstellungUUID);
        assertThat(vorstellungEntity.getAnfangszeit()).isEqualTo(anfangszeit);
        assertThat(vorstellungEntity.getId()).isEqualTo(vorstellungId);
    }

    @Test
    public void vorstellungEntityToVorstellung() {
        // arrange
        VorstellungEntity vorstellungEntity = new VorstellungEntity(vorstellungId, vorstellungUUID, anfangszeit, saalName);

        // act
        Vorstellung vorstellung = vorstellungMapper.vorstellungEntityToVorstellung(vorstellungEntity);

        // assert
        assertThat(vorstellung.saal().name()).isEqualTo(saal);
        assertThat(vorstellung.filmname()).isEqualTo(filmname);
        assertThat(vorstellung.uuid()).isEqualTo(vorstellungUUID);
        assertThat(vorstellung.anfangszeit().zeitpunkt()).isEqualTo(anfangszeit);
    }
}