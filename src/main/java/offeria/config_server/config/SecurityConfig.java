package offeria.config_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the Config Server.
 * Enforces Basic Authentication for all configuration and management endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF for microservices/API (Spring Cloud Config clients will use Basic Auth)
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Public health check and info endpoints for Kubernetes/Liveness probes
                .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                // All other config endpoints require authentication
                .anyRequest().authenticated()
            )
            // Use Basic Authentication
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
