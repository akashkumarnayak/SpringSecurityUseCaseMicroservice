package org.springsecurity.microserviceusecases.springapigateway.filters;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/v1/auth/signin",
            "/v1/auth/signup",
            "/v1/product/get-all"
    );

    public static final Map<String, List<String>> securedEndpointsWithRoles = Map.of(
            "/v1/product/", List.of("ROLE_ADMIN"),
            "/v1/user/",    List.of("ROLE_ADMIN"),
            "/v1/auth/signout", List.of("ROLE_USER", "ROLE_ADMIN")
    );

    public Predicate<ServerHttpRequest> isSecured = request ->
            !openApiEndpoints.stream()
                    .anyMatch(uri -> request.getURI().getPath().contains(uri));
}
