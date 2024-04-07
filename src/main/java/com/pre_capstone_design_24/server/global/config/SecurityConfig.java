package com.pre_capstone_design_24.server.global.config;

import com.pre_capstone_design_24.server.global.auth.JwtExceptionFilter;
import com.pre_capstone_design_24.server.global.auth.JwtFilter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    private final JwtExceptionFilter jwtExceptionFilter;

    private static final String[] ALLOWED_URL = {
            "/",
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/owner",
            "/auth/login",
            "/resources/files/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                auth -> {
                    auth
                        .requestMatchers(ALLOWED_URL).permitAll()
                        .anyRequest().hasRole("USER");
                }
            )
            .cors(
                cors -> cors.configurationSource(
                        corsConfigurationSource()
                )
            )
            .csrf(CsrfConfigurer::disable)
            .headers(httpSecurityHeadersConfigurer ->
                    httpSecurityHeadersConfigurer.frameOptions(
                            HeadersConfigurer.FrameOptionsConfig::disable)
            )
            .addFilterBefore(jwtExceptionFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(jwtFilter, JwtExceptionFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}