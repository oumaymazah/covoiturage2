package com.example.covoiturage.Services;

import com.example.covoiturage.Repositories.*;
import com.example.covoiturage.entities.*;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ConducteurService {


    private final TrajetRepository trajetRepository;
    private final ReservationRepository reservationRepository;
    private final ReclamationRepository reclamationRepository;
    private final VehiculeRepository vehiculeRepository;
    private final ConducteurRepository conducteurRepository;

    // Constructeur explicite pour l'injection des dépendances
    @Autowired
    public ConducteurService(TrajetRepository trajetRepository,
                             ReservationRepository reservationRepository,
                             ReclamationRepository reclamationRepository,
                             VehiculeRepository vehiculeRepository,ConducteurRepository conducteurRepository) {
        this.trajetRepository = trajetRepository;
        this.reservationRepository = reservationRepository;
        this.reclamationRepository = reclamationRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.conducteurRepository=conducteurRepository;
    }
    // Publier un trajet
    public Trajet publierTrajet(Trajet trajet, int conducteurId) {
        // Vérifier que le conducteur existe
        Conducteur conducteur = conducteurRepository.findById(conducteurId)
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));

        // Associer le conducteur au trajet
        trajet.setConducteur(conducteur);

        // Sauvegarder le trajet
        return trajetRepository.save(trajet);
    }


    // Consulter les trajets du conducteur
    public List<Trajet> consulterTrajets(int conducteurId) {
        return trajetRepository.findByConducteur_Id(conducteurId);
    }

    // Accepter une réservation
    public Reservation accepterReservation(int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setStatus(Reservation.ReservationStatus.ACCEPTEE);
        return reservationRepository.save(reservation);
    }

    // Répondre à une réclamation
    public Reclamation repondreReclamation(int reclamationId, String reponse) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée"));
        reclamation.setReponse(reponse);
        return reclamationRepository.save(reclamation);
    }
    // Méthode pour récupérer toutes les réservations
    public List<Reservation> consulterToutesLesReservations() {
        return reservationRepository.findAll();
    }
    // Obtenir la liste des véhicules du conducteur
   /* public List<Vehicule> obtenirVehicules(Long conducteurId) {
        return vehiculeRepository.findByConducteur_Id(conducteurId);
    }*/
}
