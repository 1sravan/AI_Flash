package org.springai.flash.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springai.flash.service.JwtService;
import org.springai.flash.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                if (token == null || token.isEmpty()) {
                      throw new IllegalArgumentException("Token is missing or empty");
                }

                try {
                    username = jwtService.extractUserName(token);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Failed to extract username from the token", e);
                }
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    MyUserDetailsService userDetailsService = context.getBean(MyUserDetailsService.class);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    } else {
                        throw new SecurityException("Invalid token");
                    }
                } catch (Exception e) {
                    throw new IllegalStateException("Authentication failed for user: " + username, e);
                }
            }

        filterChain.doFilter(request, response);
    }
}