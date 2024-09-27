package com.PoloIT.GestionDeInscripciones.Jwt;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {

    private static final String private_key = "2ns7utNmJSCwdrx6KZpVEqUFdyoHMJoqGXQR7328y2Fi4UL2ggCKS4d8ZRIw8QHL";
    private static final long jwtExpiration = 7200000;
    private static final long jwtExpirationResetPassword = 300000;

    public String generateJwt(String email) {
        Map<String, Objects> claims = new HashMap<>();
        return generateJwt(email, claims);
    }

    public String generateJwt(String email, Map<String, Objects> claims) {
        Date cuurentDate = new Date();
        Date ExpirateDate = new Date(cuurentDate.getTime() + jwtExpiration);

        String token = Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(cuurentDate)
                .expiration(ExpirateDate)
                .signWith(getKey(), SignatureAlgorithm.HS256).
                compact();


        return "Bearer " + token;
    }

    public String tokenResetPassword(String email) {
        Date cuurentDate = new Date();
        Date ExpirateDate = new Date(cuurentDate.getTime() + jwtExpirationResetPassword);

        String token = Jwts.builder()
                .subject(email)
                .issuedAt(cuurentDate)
                .expiration(ExpirateDate)
                .signWith(getKey(), SignatureAlgorithm.HS256).
                compact();

        return "Bearer " + token;
    }

    public String extractJwt(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public Boolean validateJwt(String token) {
        Jwts.parser()
                .verifyWith((SecretKey) getKey())
                .build()
                .parse(token);
        return true;
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(private_key));
    }

}
