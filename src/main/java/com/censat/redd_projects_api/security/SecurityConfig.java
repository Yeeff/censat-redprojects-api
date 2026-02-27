package com.censat.redd_projects_api.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configurando seguridad HTTP...");
        
        http.csrf().disable()
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/**").permitAll()
                // Allow GET requests without authentication - including MVT tile URLs with {z}/{x}/{y}
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                // All other requests require authentication
                .anyRequest().authenticated()
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        logger.info("Configuración de seguridad completada. Rutas públicas: /api/auth/**, GET /api/**");
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        logger.debug("Configurando CORS...");
        
        CorsConfiguration configuration = new CorsConfiguration();
        // Use wildcard for development
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        logger.debug("CORS configurado: métodos permitidos: GET, POST, PUT, DELETE, OPTIONS");
        
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Inicializando codificador de contraseñas BCrypt");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        logger.info("Inicializando AuthenticationManager");
        return config.getAuthenticationManager();
    }
}
