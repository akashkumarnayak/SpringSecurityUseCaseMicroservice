package org.springsecurity.microserviceusecases.springapigateway.filters;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springsecurity.microserviceusecases.springapigateway.exceptions.TokenNotProvidedxception;
import org.springsecurity.microserviceusecases.springapigateway.security.JwtService;

import java.util.List;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private Validator validator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtService jwtService;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if(routeValidator.isSecured.test(exchange.getRequest())) {

                if(!exchange.getRequest().getHeaders().containsKey("Authorization")) {
                    throw new TokenNotProvidedxception("Token not provided for authentication");
                }

                String tokenAuthHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if(tokenAuthHeader != null && tokenAuthHeader.startsWith("Bearer "))
                {
                    String token = tokenAuthHeader.substring(7);
                    jwtService.validateToken(token, exchange.getRequest().getPath().toString());
                }

            }
            return chain.filter(exchange);
        });
    }

    public static class Config { }
}
