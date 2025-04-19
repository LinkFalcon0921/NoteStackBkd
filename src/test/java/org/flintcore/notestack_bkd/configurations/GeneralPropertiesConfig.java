package org.flintcore.notestack_bkd.configurations;

import org.flintcore.notestack_bkd.configurations.properties.SpringProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

@TestConfiguration
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@TestPropertySource({
        "classpath:application-test.properties",
        "classpath:secrets/*-test.properties",
})
public class GeneralPropertiesConfig {
    @Bean
    @Primary
    protected SpringProperties springProperties(Environment environment) {
        Binder binder = Binder.get(environment);

        SpringProperties.Application app = binder
                .bind("spring.application", SpringProperties.Application.class)
                .orElseThrow(() -> new RuntimeException("Could not bind SpringProperties application properties"));

        return new SpringProperties(app);
    }
}
