package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
  @Value("${app.jwt-secret}")
  private String jwtSecretKey;
  @Value("${app.jwt-expiration-milliseconds}")
  private int jwtExpirationInMs;

  //generate token
  public String generateToken(Authentication authentication){
      String userName=authentication.getName();
      Date currentDate=new Date();
      Date expiryDate=new Date(currentDate.getTime()+jwtExpirationInMs);

      return Jwts.builder()
              .setSubject(userName)
              .setIssuedAt(new Date())
              .setExpiration(expiryDate)
              .signWith(SignatureAlgorithm.HS512,jwtSecretKey)
              .compact();
  }

  //get UserName from Token
    public String getUserNameFromJwt(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //Validate Jwt Token
    public boolean validateToken(String token){
      try  {
        Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
        return true;
           }
      catch (SignatureException ex){
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
          throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }

      }


}
