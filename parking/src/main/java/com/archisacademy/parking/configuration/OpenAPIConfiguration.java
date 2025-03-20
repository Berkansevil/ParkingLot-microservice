package com.archisacademy.parking.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;


@Configuration
public class OpenAPIConfiguration {

    // http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");


        License license = new License();
        license.name("Apache 2.0");
        license.url("google.com");

        Contact contact = new Contact();
        contact.setName("Mehmet Fatih Şimşek");
        contact.setUrl("https://github.com/fatihsimssek");
        contact.setEmail("mm.fatihsimsek@gmail.com");

        Info info = new Info()
                .title("Parking Management API")
                .version("1.0.0")
                .description("API for managing parking spaces and reservations.")
                .termsOfService("https://parking.com/terms")
                .license(license)
                .contact(contact);

        return new OpenAPI().servers(List.of(server)).info(info);
    }
}