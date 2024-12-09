package com.example.covoiturage.Services;


import com.example.covoiturage.Repositories.UtilisateurRepository;
import com.example.covoiturage.Repositories.VehiculeRepository;
import com.example.covoiturage.entities.*;
import com.example.covoiturage.Token.JwtTokenUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;  // Pour générer un token JWT
    private final CustomUserDetailsService customUserDetailsService;
    private final VehiculeRepository vehiculeRepository;// Pour récupérer les détails utilisateur

    // Constructeur pour l'injection des dépendances
    @Autowired
    public AuthService(UtilisateurRepository utilisateurRepository,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil,VehiculeRepository vehiculeRepository,
                       CustomUserDetailsService customUserDetailsService) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.vehiculeRepository = vehiculeRepository;
        this.customUserDetailsService = customUserDetailsService;
    }
    public Utilisateur inscrireUtilisateur(String nomComplet, String email, String motDePasse, String telephone) {
        // Vérifier si l'email existe déjà dans la base de données
        if (utilisateurRepository.existsByEmail(email)) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Créer un nouvel utilisateur (passager)
        Passager passager = new Passager();
        passager.setNomComplet(nomComplet);
        passager.setEmail(email);
        passager.setMotDePasse(passwordEncoder.encode(motDePasse));  // Encodage du mot de passe
        passager.setTelephone(telephone);
        passager.setRole(Utilisateur.Role.PASSAGER);  // Définir le rôle comme passager

        // Sauvegarder l'utilisateur dans la base de données
        return utilisateurRepository.save(passager);
    }
    public Utilisateur inscrireAdmin(String nomComplet, String email, String motDePasse, String telephone) {
        // Vérifier si l'email existe déjà dans la base de données
        if (utilisateurRepository.existsByEmail(email)) {
            throw new RuntimeException("Email déjà utilisé");
        }

        // Créer un nouvel utilisateur (passager)
        Admin admin = new Admin();
        admin.setNomComplet(nomComplet);
        admin.setEmail(email);
        admin.setMotDePasse(passwordEncoder.encode(motDePasse));  // Encodage du mot de passe
        admin.setTelephone(telephone);
        admin.setRole(Utilisateur.Role.ADMIN);  // Définir le rôle comme passager

        // Sauvegarder l'utilisateur dans la base de données
        return utilisateurRepository.save(admin);
    }

    // Inscription spécifique pour les conducteurs
    // Inscription spécifique pour les conducteurs
    public Conducteur inscrireConducteur(Conducteur conducteur) {
        // Vérifier si l'email est déjà utilisé
        if (utilisateurRepository.existsByEmail(conducteur.getEmail())) {
            throw new IllegalArgumentException("L'email est déjà utilisé");
        }

        // Validation des champs obligatoires pour le conducteur
        if (conducteur.getNomComplet() == null || conducteur.getNomComplet().isEmpty()) {
            throw new IllegalArgumentException("Le nom complet est obligatoire");
        }
        if (conducteur.getEmail() == null || conducteur.getEmail().isEmpty()) {
            throw new IllegalArgumentException("L'email est obligatoire");
        }
        if (conducteur.getMotDePasse() == null || conducteur.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est obligatoire");
        }
        if (conducteur.getTelephone() == null || conducteur.getTelephone().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de téléphone est obligatoire");
        }
        if (conducteur.getAdresse() == null || conducteur.getAdresse().isEmpty()) {
            throw new IllegalArgumentException("L'adresse est obligatoire");
        }
        if (conducteur.getCin() == null || conducteur.getCin().isEmpty()) {
            throw new IllegalArgumentException("Le CIN est obligatoire");
        }
        if (conducteur.getDateNaissance() == null || conducteur.getDateNaissance().isEmpty()) {
            throw new IllegalArgumentException("La date de naissance est obligatoire");
        }
        if (conducteur.getPermis() == null || conducteur.getPermis().isEmpty()) {
            throw new IllegalArgumentException("Le permis est obligatoire");
        }

        // Encoder le mot de passe
        conducteur.setMotDePasse(passwordEncoder.encode(conducteur.getMotDePasse()));

        // Définir le rôle et l'état du compte
        conducteur.setRole(Utilisateur.Role.CONDUCTEUR);
        conducteur.setActive(false); // Le compte doit être validé par un admin

        // Sauvegarder les informations du conducteur
        utilisateurRepository.save(conducteur);

        // Validation et enregistrement du véhicule associé
        if (conducteur.getVehicule() != null) {
            // Validation des informations du véhicule
            Vehicule vehicule = conducteur.getVehicule();
            Vehicule v1=new Vehicule();
            if (vehicule.getType() == null || vehicule.getType().isEmpty()) {
                throw new IllegalArgumentException("Le type de véhicule est obligatoire");
            }
            if (vehicule.getNum_matricule() == null || vehicule.getNum_matricule().isEmpty()) {
                throw new IllegalArgumentException("Le numéro de matricule est obligatoire");
            }

            if (vehicule.getEtat() == null) {
                throw new IllegalArgumentException("L'état du véhicule est obligatoire");
            }
            v1.setEtat(vehicule.getEtat());
            v1.setNum_matricule(vehicule.getNum_matricule());
            v1.setDegre_confort(vehicule.getDegre_confort());
            v1.setImage(vehicule.getImage());
            v1.setType(vehicule.getType());
            // Associer le véhicule au conducteur
            // Associe le véhicule au conducteur
            vehiculeRepository.save(v1); // Sauvegarde du véhicule
        } else {
            throw new IllegalArgumentException("Un véhicule est requis pour s'inscrire comme conducteur");
        }

        return conducteur;
    }



    // Authentification de l'utilisateur
    public String authentifierUtilisateur(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Vérification du mot de passe
        if (!passwordEncoder.matches(motDePasse, utilisateur.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        // Générer un JWT après l'authentification réussie
        return jwtTokenUtil.generateToken(customUserDetailsService.loadUserByUsername(email));  // Génère un JWT pour l'utilisateur
    }
}
