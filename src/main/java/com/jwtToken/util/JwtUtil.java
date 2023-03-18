package com.jwtToken.util;
//method -->for generating token method
//validate() method
//isExpire() method
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
	//this code is available on Internet with the name of JwtUtil.
	
	
	private static final long serialVersionUID=-2550185165626007488L;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	
	private String secret="pankajkumar123";
	
	
	//retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token,Claims::getSubject);
		
	}
	
	//retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
   //

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    
    
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired.
    private Boolean isTokenExpired(String token) {
    	final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
 // generate token for user;
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    
    //while creation the token:
    //1. define claims of the token, like issuer, Expiration, Subject,and the Id
    //2. Sign the JWT using the HS512 ALGOrithm and secret key.
    //3. According to JWS compact serialization(https://tools.ietf.org/html/draft-ie
    //compaction of the JWT to a URL-safe string.
    
    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }
    
    
//Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	
	

    
}
