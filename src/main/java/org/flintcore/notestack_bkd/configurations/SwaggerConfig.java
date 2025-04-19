package org.flintcore.notestack_bkd.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.flintcore.notestack_bkd.configurations.properties.OpenapiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(OpenapiProperties properties) {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.title())
                        .version(properties.version())
                        .description(properties.description())
                );
    }
}
