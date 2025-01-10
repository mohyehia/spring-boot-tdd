package com.moh.yehia.tdd;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import de.rweisleder.archunit.spring.SpringAnnotationPredicates;
import org.springframework.stereotype.Service;

@AnalyzeClasses(packagesOf = SpringBootTddApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class MethodRulesTest {
    @ArchTest
    ArchRule methods_in_controller_should_not_be_final_or_static = ArchRuleDefinition.methods()
            .that().areDeclaredInClassesThat().resideInAPackage("..controller..")
            .and().arePublic()
            .should().notBeFinal()
            .andShould().notBeStatic()
            .because("Endpoints in controllers should not be final or static");

//    @ArchTest
//    ArchRule methods_in_service_layer_should_not_be_secured = ArchRuleDefinition.noCodeUnits()
//            .that().areDeclaredInClassesThat(SpringAnnotationPredicates.springAnnotatedWith(Service.class))
//            .should().beAnnotatedWith(Secured.class);
}
