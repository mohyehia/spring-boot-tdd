package com.moh.yehia.tdd;

import com.tngtech.archunit.core.domain.properties.CanBeAnnotated;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packagesOf = SpringBootTddApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class LayerDependencyRulesTest {
    @ArchTest
    ArchRule Layers = Architectures.layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Controller").definedBy(CanBeAnnotated.Predicates.annotatedWith(RestController.class))
            .layer("Service").definedBy(CanBeAnnotated.Predicates.annotatedWith(Service.class))
            .layer("Repository").definedBy(CanBeAnnotated.Predicates.annotatedWith(Repository.class))
            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service");

    @ArchTest
    ArchRule services_should_not_access_controllers = ArchRuleDefinition.noClasses()
            .that().areAnnotatedWith(Service.class)
            .should().accessClassesThat().areAnnotatedWith(RestController.class);

    @ArchTest
    ArchRule services_should_only_be_accessed_by_controllers_or_other_services = ArchRuleDefinition.classes()
            .that().areAnnotatedWith(Service.class)
            .should().onlyBeAccessed().byAnyPackage("..controller..", "..service..");

    @ArchTest
    ArchRule repositories_should_only_be_accessed_by_services = ArchRuleDefinition.classes()
            .that().areAnnotatedWith(Repository.class)
            .should().onlyBeAccessed().byAnyPackage("..service..");

}
