package org.springsecurity.microserviceusecases.springapigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springsecurity.microserviceusecases.springapigateway.configs.AWSSecretKey;
import org.springsecurity.microserviceusecases.springapigateway.exceptions.InvalidTokenException;
import org.springsecurity.microserviceusecases.springapigateway.exceptions.UserNotAuthorizedException;
import org.springsecurity.microserviceusecases.springapigateway.filters.RouteValidator;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    private AWSSecretKey awsSecretKey;

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private RedisTemplate redisTemplate;

    Logger logger = Logger.getLogger(this.getClass().getName());

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(awsSecretKey.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Claims claims) {

        return Jwts.builder().claims()
                .add(claims)
                .and()
                .signWith(getSigningKey())
                .compact();

    }

    public void validateToken(final String token , final String path) {
        Claims jwtPayload = extractAllClaims(token);

        if(!token.equals(generateToken(jwtPayload)) || isExpired(token))
        {
            throw new InvalidTokenException("Invalid Token provided");
        }

        String jti = extractJti(token);

        if(redisTemplate.opsForValue().get(jti)!=null){
            throw new InvalidTokenException("Invalid Token provided");
        }

        List<Map<String,String>> userRoles = extractRoles(token);

        List<String> roles = userRoles.stream()
                .map(map -> map.get("authority"))
                .collect(Collectors.toList());

        for (Map.Entry<String, List<String>> entry : RouteValidator.securedEndpointsWithRoles.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                boolean hasRole = roles.stream().anyMatch(entry.getValue()::contains);
                logger.info(hasRole ? "Role found" : "Role not found");
                if (!hasRole) {
                    throw new UserNotAuthorizedException("not authorized");
                }
            }
        }
    }

    public Date extractExpiration(String authToken) {
        return extractClaim(authToken, Claims::getExpiration);
    }

    public String extractUsername(String authToken) {
        return extractClaim(authToken, Claims::getSubject);
    }

    public List<Map<String,String>> extractRoles(String authToken) {
        return extractClaim(authToken, claims -> claims.get("roles", List.class));
    }

    public String extractJti(String authToken) {
        return extractAllClaims(authToken).get("jti", String.class);
    }

    public boolean isExpired(String authToken) {
        return extractExpiration(authToken).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
