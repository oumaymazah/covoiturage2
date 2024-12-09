package com.example.covoiturage.Configuration;

import com.example.covoiturage.Token.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Désactivation de CSRF pour les API REST
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register","/api/auth/register/conducteur","/api/auth/register/admin","/api/auth/login").permitAll()  // Routes publiques pour l'authentification
                        .requestMatchers("/api/admin/reclamations","/api/admin/conducteur/{conducteurId}/valider","/api/admin/{conducteurId}/trajets").hasRole("ADMIN")  // Accès aux ressources réservées aux ADMIN
                        .requestMatchers("/api/conducteurs/{conducteurId}/trajets","/api/conducteurs/reclamation/{reclamationId}/repondre","/api/conducteurs/reservation/{reservationId}/accepter","/api/conducteurs/reservations").hasRole("CONDUCTEUR")  // Accès aux ressources réservées aux CONDUCTEUR
                        .requestMatchers("/api/passager/recherche","/api/passager/reservation","/api/passager/reclamation").hasRole("PASSAGER")  // Accès aux ressources réservées aux PASSAGER
                        .anyRequest().authenticated()  // Toute autre requête doit être authentifiée
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);  // Ajout du filtre JWT avant le filtre d'authentification de Spring

        return http.build();
    }
}