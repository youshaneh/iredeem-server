package com.shane.iredeem.service;

import com.shane.iredeem.config.Config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthService {
    final static public String USER_ID = "userId";

    @Autowired
    private Config config;

    public Map<String, Object> parseToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(getPublicKey(config.getJwtPublicKey()))
                .build();

        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @SneakyThrows
    private PublicKey getPublicKey(String key) {
        byte[] byteKey = Base64.getDecoder().decode(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }
}
