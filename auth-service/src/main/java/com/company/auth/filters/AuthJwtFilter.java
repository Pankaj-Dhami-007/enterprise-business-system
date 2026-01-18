package com.company.auth.filters;

import com.company.auth.security.jwt.JwtTokenValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthJwtFilter extends OncePerRequestFilter {

    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !(
                path.equals("/auth/api/v1/refresh") ||
                        path.equals("/auth/api/v1/logout")
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Missing Authorization header for refresh/logout");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String token = authHeader.substring(7);

        if (!jwtTokenValidator.validateToken(token)) {
            log.warn("Invalid JWT refresh token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (!jwtTokenValidator.isRefreshToken(token)) {
            log.warn("Non-refresh token used on refresh/logout endpoint");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Long userId = Long.valueOf(jwtTokenValidator.getUsername(token)); // subject = userId
        String jti = jwtTokenValidator.getJti(token);

        request.setAttribute("userId", userId);
        request.setAttribute("refreshToken", token);
        request.setAttribute("jti", jti);

        filterChain.doFilter(request, response);
    }
}
