package com.archisacademy.gatewayserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity

public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(HttpMethod.GET).permitAll()
                        .pathMatchers(HttpMethod.POST, "/microservice/api/users/register").permitAll()
                        .pathMatchers(HttpMethod.POST, "/microservice/api/users/login").permitAll()
                        .pathMatchers("/microservice/api/v1/booking/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/microservice/api/parking/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/microservice/api/v1/parkingspots/**").hasAnyRole( "USER")
                        .pathMatchers("/microservice/api/users/**").hasAnyRole("USER","ADMIN")
                        .pathMatchers("/microservice/api/vehicles/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/microservice/api/v1/parking/reservation/**").hasAnyRole("ADMIN", "USER", "SUPER_ADMIN")
                        .pathMatchers("/api/v1/payments/**").hasAnyRole("ADMIN", "USER", "SUPER_ADMIN")
                        .pathMatchers("/api/v1/reports/**").hasAnyRole("ADMIN", "USER")
                        .pathMatchers("/api/emails/**").hasAnyRole("ADMIN", "USER")
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2 -> oAuth2
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
                )
                .logout(logout -> logout
                        .logoutUrl("/microservice/api/users/logout")
                        .logoutSuccessHandler((exchange, authentication) -> {
                            Jwt jwt = (Jwt) authentication.getCredentials();
                            String accessToken = jwt.getTokenValue();
                            return WebClient.create()
                                    .post()
                                    .uri("http://localhost:8081/api/users/logout")
                                    .header("Authorization", "Bearer " + accessToken)
                                    .retrieve()
                                    .bodyToMono(Void.class)
                                    .then(Mono.fromRunnable(SecurityContextHolder::clearContext));
                        })
                );
        return serverHttpSecurity.build();
    }


    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConvertor());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


    }

