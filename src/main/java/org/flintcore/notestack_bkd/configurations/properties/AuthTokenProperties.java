package org.flintcore.notestack_bkd.configurations.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;
import java.util.function.Function;

// Can Use @DurationUnit to handle simple values but remove P in prop files

@PropertySource("classpath*:secrets/auth-token.properties")
@ConfigurationProperties(prefix = "token")
@Lazy
public record AuthTokenProperties(
       Duration expirationTime,
       Duration refreshTime,
       Duration toleranceTime
) {

    public long asSeconds(Function<AuthTokenProperties, Duration> expirationFunction) {
        return expirationFunction.apply(this).toSeconds();
    }

    public long asMinutes(Function<AuthTokenProperties, Duration> expirationFunction) {
        return expirationFunction.apply(this).toMinutes();
    }
}
