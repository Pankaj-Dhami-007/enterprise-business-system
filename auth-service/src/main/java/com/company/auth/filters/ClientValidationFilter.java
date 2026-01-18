package com.company.auth.filters;

import com.company.auth.entity.Client;
import com.company.auth.repository.ClientRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientValidationFilter extends OncePerRequestFilter {

    private final ClientRepository clientRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String clientName = request.getHeader("X-CLIENT-NAME");
        String clientSecret = request.getHeader("X-CLIENT-SECRET");
        log.debug("Client validation started for request URI: {}", request.getRequestURI());

        if(clientName == null || clientSecret == null){
            log.warn("Client headers missing. clientName={}, clientSecret={}", clientName, clientSecret);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

       Client client = clientRepository.findByClientSecretAndIsActiveTrue(clientSecret)
                .orElse(null);

        if (client == null) {
            log.warn("Invalid client secret received");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if (!client.getName().equals(clientName)) {
            log.warn("Client name mismatch. Expected={}, Received={}", client.getName(), clientName);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        log.debug("Client validation successful for client: {}", clientName);

        filterChain.doFilter(request, response);
    }
}
