package com.pre_capstone_design_24.server.global.auth;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isSwaggerUiRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = jwtProvider.resolveToken(request);
            if (jwt != null && jwtProvider.validateToken(jwt)) {
                Authentication authentication = jwtProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            throw e;
        }

        filterChain.doFilter(request, response);
    }

/*    private boolean authenticateToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Redis 쓸 건지, refresh 로직 좀 더 보충
        String accessToken = jwtProvider.resolveToken(request);

        if (accessToken == null || !jwtProvider.validateToken(accessToken))
            return false;

        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }*/

    private boolean isSwaggerUiRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs");
    }

}
