package com.moh.yehia.tdd;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.conditions.ArchConditions;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import de.rweisleder.archunit.spring.SpringAnnotationPredicates;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packagesOf = SpringBootTddApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class NamingConventionTest {
    @ArchTest
    ArchRule ControllerNaming = ArchRuleDefinition.classes()
            .that(SpringAnnotationPredicates.springAnnotatedWith(RestController.class))
            .should(ArchConditions.haveSimpleNameEndingWith("Controller"))
            .andShould(ArchConditions.resideInAPackage("..controller.."));

    @ArchTest
    ArchRule ServiceNaming = ArchRuleDefinition.classes()
            .that().areAnnotatedWith(Service.class)
            .should().haveSimpleNameEndingWith("ServiceImpl")
            .andShould(ArchConditions.resideInAPackage("..service.."));

    @ArchTest
    ArchRule RepositoryNaming = ArchRuleDefinition.classes()
            .that().areAnnotatedWith(Repository.class)
            .should().haveSimpleNameEndingWith("Repository");

}
