package com.smu.board.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	
	private final SecretKey secretKey;
	
	public JWTUtil(@Value("${jwt.secret}") String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
	}
	
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
	}
	
	public Integer getId(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Integer.class);
	}
	
	public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

	public String createJwt (String username, String role, Integer Id, Long expiredMs) {
		Date now = new Date();
	    Date expirationDate = new Date(now.getTime() + expiredMs);
		return Jwts.builder()
				.claim("username", username)
				.claim("id", Id)
				.claim("role", role.replace("ROLE_", ""))
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(expirationDate)
                .signWith(secretKey)
                .compact();
	}
}
