package org.flintcore.notestack_bkd.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring")
public record SpringProperties(
        Application application
) {
    public record Application(String name, String environment) {}
}
