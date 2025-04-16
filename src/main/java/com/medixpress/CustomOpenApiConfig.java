package com.medixpress;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(info = @Info(

        title = "MediXpress",
        version = "v1.0",
        description = "A Medical Shopping Cart",
        termsOfService = "http://example.com/terms/",
        contact = @Contact(name = "Sandeep Konjeti",
                url = "http://example.com/support",
                email = "sandeepkonjeti@example.com"),

        license = @License(
                name = "Apache",
                url = "http://www.apache.org/licenses/LICENSE-2.0")

))

public class CustomOpenApiConfig {

}


