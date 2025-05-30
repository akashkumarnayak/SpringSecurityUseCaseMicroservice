package org.springsecurity.microserviceusecases.springapigateway.filters;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springsecurity.microserviceusecases.springapigateway.security.JwtService;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ModifySignoutRequestBodyFilter extends AbstractGatewayFilterFactory<ModifySignoutRequestBodyFilter.Config> {

    public ModifySignoutRequestBodyFilter() {
        super(Config.class);
    }

    @Autowired
    private JwtService jwtService;

    @Override
    public GatewayFilter apply(Config config) {
        return new ModifyRequestBodyGatewayFilterFactory()
                .apply(new ModifyRequestBodyGatewayFilterFactory.Config()
                        .setInClass(Map.class)
                        .setOutClass(Map.class)
                        .setRewriteFunction(Map.class,Map.class,(exchange, body)->{

                            String tokenAuthHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                            String token = tokenAuthHeader.substring(7);
                            body.put("username", jwtService.extractUsername(token));
                            body.put("jti", jwtService.extractJti(token));
                            body.put("expiryDate", jwtService.extractExpiration(token));
                            return Mono.just(body);
                        }));
    }

    public static class Config{}
}
