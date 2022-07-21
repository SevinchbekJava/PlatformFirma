package com.example.firmaplatform.WebToken;

import com.example.firmaplatform.Model.Roles;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Token {
    long time = 360_000_000;
    Date expirationDate = new Date(System.currentTimeMillis()+time);
    String password = "HelloWorld";
    public String getToken(String username, Roles role){
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim("ROLES", role)
                .signWith(SignatureAlgorithm.HS512, password)
                .compact();
        return token;
    }
    public boolean tokenCheck(String token){
        Jwts
                .parser()
                .setSigningKey(password)
                .parseClaimsJws(token);
        return true;
    }

    public String userCheck(String username){
        String s = Jwts
                .parser()
                .setSigningKey(password)
                .parseClaimsJws(username)
                .getBody()
                .getSubject();
        return s;
    }
}
