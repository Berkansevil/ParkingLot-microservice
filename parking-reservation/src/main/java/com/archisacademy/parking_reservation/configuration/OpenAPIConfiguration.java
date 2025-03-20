package com.archisacademy.parking_reservation.configuration;

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

    // http://localhost:8090/swagger-ui/index.html#/
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8090");


        License license = new License();
        license.name("Apache 2.0");
        license.url("google.com");

        Contact contact = new Contact();
        contact.setName("Berkan Sevil");
        contact.setUrl("https://github.com/Berkansevil");
        contact.setEmail("b.sevil326@gmail.com");

        Info info = new Info()
                .title("Parking Reservation Service API")
                .version("1.0.0")
                .description("API for managing parking reservations.")
                .termsOfService("https://parking-management.com/reservation/terms")
                .license(license)
                .contact(contact);

        return new OpenAPI().servers(List.of(server)).info(info);
    }
}
