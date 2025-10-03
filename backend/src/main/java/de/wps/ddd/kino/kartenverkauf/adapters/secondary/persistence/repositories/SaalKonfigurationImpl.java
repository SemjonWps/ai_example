package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalKonfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SaalKonfigurationImpl implements SaalKonfiguration {

    private final SaalRepository saalRepository;

    @Override
    public Optional<SaalAbmessungen> findeAbmessungen(Saal saal) {
        return saalRepository.findByName(saal.name())
                .map(entity -> new SaalAbmessungen(entity.getReihen(), entity.getSpalten()));
    }
}