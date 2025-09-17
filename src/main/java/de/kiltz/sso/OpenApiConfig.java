package de.kiltz.sso;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
// http://localhost:8080/swagger-ui.html
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Meine REST API")
                        .description("Beschreibung der API")
                        .version("1.0.0"));
    }
}
