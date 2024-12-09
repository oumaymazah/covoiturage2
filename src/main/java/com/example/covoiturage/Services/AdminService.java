package com.example.covoiturage.Services;
import com.example.covoiturage.Repositories.ConducteurRepository;
import com.example.covoiturage.Repositories.ReclamationRepository;
import com.example.covoiturage.Repositories.TrajetRepository;
import com.example.covoiturage.Repositories.UtilisateurRepository;
import com.example.covoiturage.entities.Conducteur;
import com.example.covoiturage.entities.Reclamation;
import com.example.covoiturage.entities.Trajet;
import com.example.covoiturage.entities.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class AdminService {


    private final ConducteurRepository conducteurRepository;


    private final UtilisateurRepository utilisateurRepository;


    private final ReclamationRepository reclamationRepository;
    private final TrajetRepository trajetRepository;

    @Autowired
    public AdminService(ConducteurRepository conducteurRepository,
                        UtilisateurRepository utilisateurRepository, TrajetRepository trajetRepository,
                        ReclamationRepository reclamationRepository) {
        this.conducteurRepository = conducteurRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.reclamationRepository = reclamationRepository;
        this.trajetRepository=trajetRepository;
    }

    // Valider le compte conducteur
    public Conducteur validerConducteur(int conducteurId) {
        Conducteur conducteur = conducteurRepository.findById(conducteurId)
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));
        conducteur.setActive(true); // Activation du compte
        return conducteurRepository.save(conducteur);
    }

    // Consulter les réclamations
    public List<Reclamation> consulterReclamations() {
        return reclamationRepository.findAll();
    }

    // Générer des statistiques sur les conducteurs
    public List<Conducteur> genererStatistiquesConducteurs() {
        return conducteurRepository.findAll();  // Implémentation plus poussée si nécessaire
    }
    public List<Trajet> consulterTrajets(int conducteurId) {
        return trajetRepository.findByConducteur_Id(conducteurId);
    }

}
