package br.com.library.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

    @Configuration
    @OpenAPIDefinition(
            info = @Info(
                    title = "Library API",
                    version = "v1",
                    contact = @Contact(
                            name = "Rafael Pino",
                            email = "rafaeltsd@gmail.com"
            )
    )
    )
public class OpenApiConfiguration {


}
