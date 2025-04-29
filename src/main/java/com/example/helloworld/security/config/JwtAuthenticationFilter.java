package com.example.helloworld.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/// Marks this class as a Spring-managed component, so it can be auto-detected and added to the application context.
@Component

// This Lombok annotation automatically generates a constructor with required (final or @NonNull) fields.
// This is useful for dependency injection.
@RequiredArgsConstructor

// This class defines a custom filter that will be executed ONCE per HTTP request.
// You typically use this for tasks like JWT authentication, logging, or request validation.
// It's part of the Spring Security framework.
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    /**
     * This method is overridden from OncePerRequestFilter.
     * It's where you define the logic that should be executed for every incoming request.
     *
     * This is where you usually:
     * - Extract the JWT token from the request header
     * - Validate the token
     * - Set authentication details in the SecurityContext
     *
     * @param request the HTTP request coming in
     * @param response the HTTP response to send back
     * @param filterChain the chain of filters to continue request processing
     *
     * @throws ServletException if something goes wrong with servlet processing
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String autheHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (autheHeader == null || !autheHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = autheHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if(jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        // Your JWT filtering logic would go here
        filterChain.doFilter(request, response);
        // At the end, you should continue the filter chain so the request is processed further
//        filterChain.doFilter(request, response);
    }
}

