package de.wps.ddd.kino.filmauswahl.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;
import java.util.UUID;

@org.jmolecules.ddd.annotation.Entity
@Entity
@Table(name = "vorstellungen", schema = "filmauswahl")
@Data
@NoArgsConstructor
public class Vorstellung {
    @Identity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private UUID uuid;
    @JsonIgnore
    private Long filmId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginn;
    private Integer preis;
    @ManyToOne
    @JoinColumn(name = "saal_id", nullable = false)
    @JsonIgnore
    private Saal saal;

    @JsonProperty("saalId")
    public void setSaalId(Long saalId) {
        this.saal = new Saal();
        this.saal.setId(saalId);
    }
}
