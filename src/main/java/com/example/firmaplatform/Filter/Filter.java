package com.example.firmaplatform.Filter;

import com.example.firmaplatform.Model.Staff;
import com.example.firmaplatform.Service.StaffService;
import com.example.firmaplatform.WebToken.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    Token token;

    @Autowired
    StaffService staffService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authentication = request.getHeader("Auth");
        if (!(authentication==null) && authentication.startsWith("Bearer")){
            authentication = authentication.substring(7);
            if (token.tokenCheck(authentication)){
                System.out.println(authentication);
                String s = token.userCheck(authentication);
                UserDetails userDetails = staffService.loadUserByUsername(s);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
            else System.out.println("null");
        }
        filterChain.doFilter(request, response);
    }
}
