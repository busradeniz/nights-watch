package com.nightswatch.web.rest;


import com.nightswatch.web.rest.handler.AbstractExceptionHandler;
import org.junit.Before;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Set;

public abstract class AbstractRestServiceTest {

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        final StaticApplicationContext staticApplicationContext = new StaticApplicationContext();
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(AbstractExceptionHandler.class));
        final Set<BeanDefinition> candidateComponents = scanner.findCandidateComponents("com.nightswatch");
        for (BeanDefinition candidateComponent : candidateComponents) {
            staticApplicationContext.registerBeanDefinition(candidateComponent.getBeanClassName(), candidateComponent);
        }

        final WebMvcConfigurationSupport webMvcConfigurationSupport = new WebMvcConfigurationSupport();
        webMvcConfigurationSupport.setApplicationContext(staticApplicationContext);

        final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();
        exceptionHandlerExceptionResolver.setApplicationContext(staticApplicationContext);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(getRestService())
                .setHandlerExceptionResolvers(webMvcConfigurationSupport.handlerExceptionResolver())
                .build();
    }

    public abstract Object getRestService();
}
