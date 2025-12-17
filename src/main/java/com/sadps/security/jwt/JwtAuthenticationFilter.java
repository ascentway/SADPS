package com.sadps.security.jwt;

import com.sadps.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

//        System.out.println(">>> JWT FILTER CALLED");
        log.debug("JWT filter invoked for request: {}", request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
//        System.out.println(">>> Authorization Header:" + authHeader);
        log.debug("Authorization Header");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
//            System.out.println(">>> No Bearer Token Found");
            log.debug("No Bearer Token found in request");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
//        System.out.println(">>> JWT:" + jwt);
        final String userEmail;

        try {
            userEmail = jwtService.extractEmail(jwt);
//            System.out.println(">>> Extracted eMAIL:"+ userEmail);
            log.debug("Extract email from JWT");
        } catch (Exception e){
//            System.out.println(">>> Token Parsing Failed");
            log.warn("JWT parsing failed");
            filterChain.doFilter(request, response);
            return;
        }

        if (userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication()==null){

            var userDetails =
                    userDetailsService.loadUserByUsername(userEmail);

//            System.out.println(">>> User Loaded:" + userDetails.getUsername());
            log.debug("JWT validated successfully for {}", userEmail);

            if(jwtService.isTokenValid(jwt,userDetails)){

                System.out.println(">>> Token is VALID");

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
            else {
//                System.out.println(">>> Token is INVALID");
                log.warn("Invalid JWT Token Detected");
            }
        }
        filterChain.doFilter(request,response);
    }
}
