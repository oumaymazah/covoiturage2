package com.example.covoiturage.Services;

import com.example.covoiturage.Repositories.*;
import com.example.covoiturage.entities.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PassagerService {


    private final TrajetRepository trajetRepository;
    private final ReservationRepository reservationRepository;
    private final ReclamationRepository reclamationRepository;
    private final PassagerRepository passagerRepository;
    private final ConducteurRepository conducteurRepository;

    // Constructeur explicite pour l'injection des dépendances
    @Autowired
    public PassagerService(TrajetRepository trajetRepository,
                           ReservationRepository reservationRepository,
                           ReclamationRepository reclamationRepository,
                           PassagerRepository passagerRepository,
                           ConducteurRepository conducteurRepository) {
        this.trajetRepository = trajetRepository;
        this.reservationRepository = reservationRepository;
        this.reclamationRepository = reclamationRepository;
        this.passagerRepository = passagerRepository;
        this.conducteurRepository = conducteurRepository;
    }

    // Rechercher des trajets
    public List<Trajet> rechercherTrajets(String villeDepart, String villeArrivee, String dateDepart) {
        return trajetRepository.findByVilleDepartAndVilleArriveeAndDateDepart(villeDepart, villeArrivee, dateDepart);
    }

    // Réserver un trajet
    public Reservation reserverTrajet(int trajetId, int passagerId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));

        // Vérifier la disponibilité des places
        if (trajet.getPlacesDisponibles() > 0) {
            // Créer la réservation
            Reservation reservation = new Reservation();

            // Associer le passager (au lieu de juste définir son ID)
            Passager passager = passagerRepository.findById(passagerId)
                    .orElseThrow(() -> new RuntimeException("Passager non trouvé"));
            reservation.setPassager(passager); // Utilisation de l'objet Passager

            // Associer le trajet
            reservation.setTrajet(trajet);

            // Définir le statut de la réservation
            reservation.setStatus(Reservation.ReservationStatus.EN_ATTENTE);

            // Mettre à jour la disponibilité des places dans le trajet
            trajet.setPlacesDisponibles(trajet.getPlacesDisponibles() - 1);
            trajetRepository.save(trajet);

            // Sauvegarder la réservation
            return reservationRepository.save(reservation);
        } else {
            throw new RuntimeException("Aucune place disponible");
        }
    }


    // Déposer une réclamation
    public Reclamation deposerReclamation(int passagerId, int conducteurId, String objet, String description) {
        // Créer la réclamation
        Reclamation reclamation = new Reclamation();

        // Associer le passager (au lieu de juste définir son ID)
        Passager passager = passagerRepository.findById(passagerId)
                .orElseThrow(() -> new RuntimeException("Passager non trouvé"));
        reclamation.setPassager(passager); // Utilisation de l'objet Passager

        // Associer le conducteur (au lieu de juste définir son ID)
        Conducteur conducteur = conducteurRepository.findById(conducteurId)
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));
        reclamation.setConducteur(conducteur); // Utilisation de l'objet Conducteur

        // Définir l'objet et la description de la réclamation
        reclamation.setObjet(objet);
        reclamation.setDescription(description);

        // Sauvegarder la réclamation
        return reclamationRepository.save(reclamation);
    }

}
