package com.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user-service-auth-route", r -> r.path("/auth/**")
						.filters(f -> f.rewritePath("/auth/(?<segment>.*)", "/api/auth/${segment}"))
						.uri("lb://user-service"))

				.route("user-service-users-route", r -> r.path("/usuarios/**")
						.filters(f -> f.rewritePath("/usuarios(?<segment>/?.*)", "/api/usuarios${segment}"))
						.uri("lb://user-service"))

				.route("project-service-route", r -> r.path("/projetos/**")
						.filters(f -> f.rewritePath("/projetos(?<segment>/?.*)", "/api/projetos${segment}"))
						.uri("lb://project-service"))

				.route("group-service-route", r -> r.path("/grupos/**")
						.filters(f -> f.rewritePath("/grupos(?<segment>/?.*)", "/api/grupos${segment}"))
						.uri("lb://group-service"))
				.build();
	}
}
