package com.usmantech.taskify.auth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Component
public class JWTUtil {
	 private SecretKey SECRET_KEY = Keys.hmacShaKeyFor("TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V".getBytes());

	 public String generateToken(String username) {
		 Map<String, Object> claims=new HashMap<String, Object>();
		 return createToken(claims,username);
	}

	 private String createToken(Map<String, Object> claims, String username) {
		 
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.header().empty().add("typ","JWT")
				.and()
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(SECRET_KEY)
				.compact();
				
	 }
	 public String extractUsername(String token) {
			Claims claims=extractAllClaims(token);
			return claims.getSubject();
			
		}
		 
	private Claims extractAllClaims(String token) {
			return Jwts.parser()
					.verifyWith(SECRET_KEY)
					.build()
					.parseSignedClaims(token)
					.getPayload();
		}

	 public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	 }

	 private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	 }

	 public Date extractExpiration(String token) {
		return extractAllClaims(token).getExpiration();
	 }
	
}
