package com.moh.yehia.tdd;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.ProxyRules;
import com.tngtech.archunit.library.freeze.FreezingArchRule;
import de.rweisleder.archunit.spring.framework.SpringAsyncRules;
import de.rweisleder.archunit.spring.framework.SpringCacheRules;
import de.rweisleder.archunit.spring.framework.SpringComponentRules;
import org.springframework.scheduling.annotation.Async;

@AnalyzeClasses(packagesOf = SpringBootTddApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
public class ProxyRulesTest {
    // freeze mechanism is helpful to add some TODO comments
    // also useful for legacy projects
    @ArchTest
    ArchRule Caching = FreezingArchRule.freeze(SpringCacheRules.CacheableMethodsNotCalledFromSameClass);

    @ArchTest
    ArchRule no_bypass_of_proxy_logic = ProxyRules.no_classes_should_directly_call_other_methods_declared_in_the_same_class_that_are_annotated_with(Async.class);

    @ArchTest
    ArchRule async_methods_not_called_from_same_class = SpringAsyncRules.AsyncMethodsNotCalledFromSameClass;

    @ArchTest
    ArchRule dependencies_of_Services = SpringComponentRules.DependenciesOfServices;

    @ArchTest
    ArchRule dependencies_of_repositories = SpringComponentRules.DependenciesOfRepositories;
}
