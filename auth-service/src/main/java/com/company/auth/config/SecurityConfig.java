package com.company.auth.config;

import com.company.auth.filters.AuthJwtFilter;
import com.company.auth.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthJwtFilter authJwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

           http
                //CSRF not needed (JWT based)
                .csrf(csrf -> csrf.disable())
                //No HTTP session
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/api/v1/login"
                        ).permitAll()

                        .requestMatchers(
                                "/auth/api/v1/refresh",
                                "/auth/api/v1/logout"
                        ).authenticated()

                        .anyRequest().denyAll()
                )
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(
                        authJwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(customUserDetailsService);
//        provider.setPasswordEncoder(passwordEncoder());
//        return provider;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
