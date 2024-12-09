package com.example.covoiturage.Token;



import com.example.covoiturage.Services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Extraire le token JWT du header Authorization
        String token = null;
        String email = null;

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);  // Supprimer "Bearer " du début du token
            email = jwtTokenUtil.extractUsername(token);  // Extraire l'email du token
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Vérifier si le token est valide
            if (jwtTokenUtil.validateToken(token, userDetailsService.loadUserByUsername(email))) {

                // Créer un nouvel objet d'authentification avec le token
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                email, null, userDetailsService.loadUserByUsername(email).getAuthorities());

                // Ajouter les détails d'authentification
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Mettre l'authentification dans le contexte de sécurité de Spring
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        // Continuer la chaîne de filtres
        chain.doFilter(request, response);
    }
}
