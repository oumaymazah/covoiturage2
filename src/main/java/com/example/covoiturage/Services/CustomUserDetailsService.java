package com.example.covoiturage.Services;
import com.example.covoiturage.Repositories.UtilisateurRepository;
import com.example.covoiturage.entities.Utilisateur;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;



@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Récupérer l'utilisateur par son email
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + email));

        // Récupérer les rôles et ajouter le préfixe "ROLE_" pour chaque rôle
        String role =  utilisateur.getRole().name();  // Exemple de formatage du rôle

        // Créer et retourner l'objet UserDetails avec les rôles de l'utilisateur
        return User.builder()
                .username(utilisateur.getEmail())
                .password(utilisateur.getMotDePasse())
                .roles(role) // Ajouter le rôle à l'utilisateur
                .build();
    }
}
