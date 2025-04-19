package org.flintcore.notestack_bkd.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath*:properties/openapi.properties")
@ConfigurationProperties(prefix = "openapi")
public record OpenapiProperties(
        String title, String description, String version
) {}
