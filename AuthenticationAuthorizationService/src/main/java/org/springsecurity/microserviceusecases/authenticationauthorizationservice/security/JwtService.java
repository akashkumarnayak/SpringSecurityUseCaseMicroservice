package org.springsecurity.microserviceusecases.authenticationauthorizationservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.configs.AWSSecretKey;
import org.springsecurity.microserviceusecases.authenticationauthorizationservice.dtos.AwsSecretManager;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtService {

    @Autowired
    private AWSSecretKey awsSecretKey;


    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(awsSecretKey.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", userDetails.getUsername()+ UUID.randomUUID());
        claims.put("roles", userDetails.getAuthorities());
        return Jwts.builder().claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .and()
                .signWith(getSigningKey())
                .compact();
    }
}
