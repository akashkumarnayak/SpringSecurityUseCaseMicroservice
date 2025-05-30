package org.springsecurity.microserviceusecases.springapigateway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springsecurity.microserviceusecases.springapigateway.filters.AuthenticationFilter;
import org.springsecurity.microserviceusecases.springapigateway.filters.ModifySignoutRequestBodyFilter;

@Configuration
public class ApiGatewayConfiguration {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private ModifySignoutRequestBodyFilter modifySignoutRequestBodyFilter;

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {

        return builder.routes()
                .route(p->p.path("/v1/auth/signout")
                        .filters(f-> f.filter(authenticationFilter.apply(authenticationFilter.newConfig()))
                                .filter(modifySignoutRequestBodyFilter.apply(modifySignoutRequestBodyFilter.newConfig())))
                        .uri("lb://AuthenticationAuthorizationService"))
                .route(p -> p.path("/v1/product/**")
                        .filters(f->f.filter(authenticationFilter.apply(authenticationFilter.newConfig())))
                        .uri("lb://ProuctService"))
                .route(p -> p.path("/v1/user/**")
                        .filters(f->f.filter(authenticationFilter.apply(authenticationFilter.newConfig())))
                        .uri("lb://UserService"))
                .route(p->p.path("/v1/auth/**")
                        .filters(f->f.filter(authenticationFilter.apply(authenticationFilter.newConfig())))
                        .uri("lb://AuthenticationAuthorizationService"))
                .build();
    }
}
