package de.wps.ddd.kino.saalkonfiguration.service;

import de.wps.ddd.kino.common.architecture.ApplicationService;
import de.wps.ddd.kino.common.fixtures.FixtureInstaller;
import de.wps.ddd.kino.saalkonfiguration.data.SaalConfiguration;
import de.wps.ddd.kino.saalkonfiguration.data.SaeleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ApplicationService
@Service
@RequiredArgsConstructor
public class SaalkonfigurationService {

    private final SaeleRepository saeleRepository;
    private final FixtureInstaller fixtureInstaller;

    public List<SaalConfiguration> alleSaele() {
        return saeleRepository.findAll();
    }

    @Transactional
    public SaalConfiguration saveSaal(SaalConfiguration saal) {
        SaalConfiguration savedSaal = saeleRepository.save(saal);
        fixtureInstaller.run(null);
        return savedSaal;
    }

    @Transactional
    public void deleteSaal(String name) {
        saeleRepository.deleteByName(name);
        fixtureInstaller.run(null);
    }
}
