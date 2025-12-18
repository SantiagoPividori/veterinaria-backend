package com.pividori.veterinaria.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//TODO: #Poner la SECRET_KEY en una variable de entorno.

//Firma y valída tokens, también extrae la información.
@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private long tokenExpirationInMs;
    @Value("${security.jwt.refresh.expiration}")
    private long refreshTokenExpirationInMs;

    //Con este metodo transformamos el string a una key válida para utilizar
    private SecretKey getSigningKey(){
        //Pasamos él string a un array de bytes porque los algoritmos de firma trabajan con byte no con string
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        //Es un helper de JJWT que arma un objeto Key compatible con HMAC(HS256).
        //Valida, entre otras cosas, que la key tenga suficiente longitud para ser segura.
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(UserDetails userDetails, long expirationMs, String tokenType) {
        return Jwts.builder()
                //De quién es este token?
                .subject(userDetails.getUsername())
                //Claim es una parte del payload, que sería toda la información que mandamos, entonces esta es una parte de toda la información.
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .claim("type", tokenType)
                //Cuando creamos él token
                .issuedAt(new Date(System.currentTimeMillis()))
                //Cuando expira el token
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                //Firmado con: getSigningKey(), que sería nuestra secret key.
                .signWith(getSigningKey())
                //¿Resultado? Un string con toda la información.
                .compact();
    }

    public String extractUsername(String token){
        //Con esto leemos el sub del token. El sub sería el subject(de quien es el token)
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails, tokenExpirationInMs, "access_token");
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(userDetails, refreshTokenExpirationInMs, "refresh_token");
    }

    public Long getTokenExpirationInMs() {
        return tokenExpirationInMs;
    }

    public Long getRefreshTokenExpirationInMs() {
        return refreshTokenExpirationInMs;
    }
}