package com.example.covoiturage.Controller;
import com.example.covoiturage.Services.AuthService;
import com.example.covoiturage.entities.Admin;
import com.example.covoiturage.entities.Conducteur;
import com.example.covoiturage.entities.Passager;
import com.example.covoiturage.entities.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    // Constructeur avec @Autowired
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    // Endpoint pour inscrire un utilisateur (passager par défaut)
    @PostMapping("/register")
    public ResponseEntity<Utilisateur> inscrirePassager(@RequestBody Passager passager) {
        try {
            Utilisateur utilisateur = authService.inscrireUtilisateur(passager.getNomComplet(), passager.getEmail(),
                    passager.getMotDePasse(), passager.getTelephone());
            return ResponseEntity.ok(utilisateur);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/register/admin")
    public ResponseEntity<Utilisateur> inscrireAdmin(@RequestBody Admin admin) {
        try {
            Utilisateur utilisateur = authService.inscrireAdmin(admin.getNomComplet(), admin.getEmail(),
                    admin.getMotDePasse(), admin.getTelephone());
            return ResponseEntity.ok(utilisateur);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    // Endpoint pour inscrire un conducteur
    @PostMapping("/register/conducteur")
    public ResponseEntity<String> registerConducteur(@RequestBody Conducteur conducteur) {
        try {
            // Inscrire le conducteur
            authService.inscrireConducteur(conducteur);
            return ResponseEntity.ok("Conducteur inscrit avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Gestion des erreurs
        }
    }

    // Endpoint pour l'authentification de l'utilisateur (connexion)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        try {
            // Authentifier l'utilisateur et générer un token JWT
            String jwtToken = authService.authentifierUtilisateur(request.getEmail(), request.getMotDePasse());
            return ResponseEntity.ok(jwtToken);  // Retourne le token JWT
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());  // Gestion des erreurs
        }
    }

    // Classe interne pour les informations de connexion
    public static class LoginRequest {
        private String email;
        private String motDePasse;

        // Getters et Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMotDePasse() {
            return motDePasse;
        }

        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }
    }
}
