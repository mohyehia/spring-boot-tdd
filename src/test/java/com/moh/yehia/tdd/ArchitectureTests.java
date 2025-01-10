package com.moh.yehia.tdd;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.DependencyRules;
import com.tngtech.archunit.library.GeneralCodingRules;
import com.tngtech.archunit.library.freeze.FreezingArchRule;
import de.rweisleder.archunit.spring.framework.SpringCacheRules;
import jakarta.persistence.Entity;

@AnalyzeClasses(packagesOf = SpringBootTddApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTests {
    @ArchTest
    ArchRule repositories_must_Reside_in_repository_package = ArchRuleDefinition.classes()
            .that().haveNameMatching(".*Repository")
            .should().resideInAPackage("..repository..")
            .as("Repositories must reside in 'repository' package");

    @ArchTest
    ArchRule EntityLocation = ArchRuleDefinition.classes()
            .that().areAnnotatedWith(Entity.class)
            .should().resideInAPackage("..model.entity..");

    @ArchTest
    ArchRule NoAccessToStandardStreams = GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    @ArchTest
    ArchRule TestClassesShouldResideInTheSamePackageAsImplementation = GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation();

    @ArchTest
    ArchRule NoFieldInjection = GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;


    @ArchTest
    ArchRule NoGenericExceptions = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    ArchRule no_access_to_upper_package = DependencyRules.NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES;

    // load all the tests from another classes as ArchTests
    @ArchTest
    ArchTests archTests = ArchTests.in(LayerDependencyRulesTest.class);

}
