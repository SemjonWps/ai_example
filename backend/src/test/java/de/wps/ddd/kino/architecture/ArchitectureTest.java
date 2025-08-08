package de.wps.ddd.kino.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.jmolecules.archunit.JMoleculesDddRules;

@AnalyzeClasses(packages = "de.wps.ddd.kino", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

    @ArchTest
    @SuppressWarnings("unused")
    ArchRule dddRules = JMoleculesDddRules.all();

    @ArchTest
    @SuppressWarnings("unused")
    ArchRule hexagonalRules = JMoleculesArchitectureRules.ensureHexagonal();
}
