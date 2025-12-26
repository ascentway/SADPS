package com.sadps.security.filters;


import com.sadps.config.RateLimiterConfig;
import com.sadps.exceptions.RateLimitExceededException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Map<String, RateLimiterConfig.RequestCounter> counterMap;
    private static final int LOGIN_LIMIT = 5;
    private static final long WINDOW_MS = 60_000;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path.startsWith("/auth/login")){
            applyRateLimit(request);
        }

        filterChain.doFilter(request, response);
    }

    private void applyRateLimit(HttpServletRequest request){
        String ip = request.getRemoteAddr();
        long now = System.currentTimeMillis();

        RateLimiterConfig.RequestCounter counter = counterMap.get(ip);

        if (counter == null || now - counter.windowStart > WINDOW_MS){
            counterMap.put(ip, new RateLimiterConfig.RequestCounter(1, now));
            return;
        }
        counter.count++;

        if (counter.count > LOGIN_LIMIT){
            throw new RateLimitExceededException(
                    "Too Many Login Attempts Try Again"
            );
        }
    }

}
