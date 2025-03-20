package com.archisacademy.stripeservice.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenAPIConfiguration {

    // http://localhost:8082/swagger-ui/index.html#/
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8082");


        License license = new License();
        license.name("Apache 2.0");
        license.url("google.com");

        Contact contact = new Contact();
        contact.setName("Mehmet Fatih Şimşek");
        contact.setUrl("https://github.com/fatihsimssek");
        contact.setEmail("mm.fatihsimsek@gmail.com");

        Info info = new Info()
                .title("Payment Service API")
                .version("1.0.0")
                .description("API for managing payments.")
                .termsOfService("https://parking-management.com/payment/terms")
                .license(license)
                .contact(contact);

        return new OpenAPI().servers(List.of(server)).info(info);
    }
}