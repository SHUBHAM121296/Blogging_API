package com.blog.api.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	
	public static final long JWT_TOKEN_VALIDITY = 5*60*60 ;
	
	public String secret = "jwtTokenKey";
	
	// extracting username from token
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	// retrieving expiration date from token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token,Claims::getExpiration);
	}
	
	// this will provide us claim for the given token
	public<T> T getClaimFromToken(String token,Function<Claims,T> claimsResolver) {
		final Claims claims=getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	// to retrieve any information from the token we will have to pass secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration=getExpirationDateFromToken(token);
		return  expiration.before(new Date());
	}
	
	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims=new HashMap<>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
	private String doGenerateToken(Map<String,Object> claims,String subject) {
		 return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public boolean validateToken(String token,UserDetails userDetails) {
		final String userName=getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !(isTokenExpired(token)));
	}
	
}
