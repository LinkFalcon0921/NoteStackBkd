package org.flintcore.notestack_bkd.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath*:secrets/secret-key.properties")
@ConfigurationProperties(prefix = "auth")
@Lazy
public record SecretsProperties(
        String secret
) {
}
