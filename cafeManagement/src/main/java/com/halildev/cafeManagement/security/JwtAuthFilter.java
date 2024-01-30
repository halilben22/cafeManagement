package com.halildev.cafeManagement.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {


    private final JwtUtils jwtUtils;
    Claims claims = null;
    String userName = null;
    private final CustomerUserDetailsService customerUserDetailsService;

    public JwtAuthFilter(JwtUtils jwtUtils, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtUtils = jwtUtils;

        this.customerUserDetailsService = customerUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;



        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userName = jwtUtils.extractUsername(token);
            claims = jwtUtils.extractAllClaims(token);
        }


        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = customerUserDetailsService.loadUserByUsername(userName);

            if (jwtUtils.validateToken(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }

        filterChain.doFilter(request, response);


    }


    //CHECKING AUTHORIZE WITHIN ADMIN AND USER
    public boolean isAdmin() {

        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {

        return "user".equalsIgnoreCase((String) claims.get("role"));
    }

    //GETTING CURRENT USERNAME
    public String getCurrentUser(){
        return userName;
    }

}
