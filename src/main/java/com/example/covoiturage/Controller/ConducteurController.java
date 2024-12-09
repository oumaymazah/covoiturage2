package com.example.covoiturage.Controller;
import com.example.covoiturage.entities.Reclamation;
import com.example.covoiturage.entities.Reservation;
import com.example.covoiturage.entities.Trajet;
import com.example.covoiturage.entities.Vehicule;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.covoiturage.Services.ConducteurService;

import java.util.List;

@RestController
@RequestMapping("/api/conducteurs")
public class ConducteurController {

    private final ConducteurService conducteurService;

    // Constructeur explicite pour l'injection des dépendances
    @Autowired
    public ConducteurController(ConducteurService conducteurService) {
        this.conducteurService = conducteurService;
    }

    // Publier un trajet
    @PostMapping("/{conducteurId}/trajets")
    public ResponseEntity<Trajet> publierTrajet(@PathVariable int conducteurId, @RequestBody Trajet trajet) {
        Trajet nouveauTrajet = conducteurService.publierTrajet(trajet, conducteurId);
        return ResponseEntity.ok(nouveauTrajet);
    }

    // Consulter les trajets du conducteur
    @GetMapping("/{conducteurId}/trajets")
    public ResponseEntity<List<Trajet>> consulterTrajets(@PathVariable int conducteurId) {
        List<Trajet> trajets = conducteurService.consulterTrajets(conducteurId);
        return ResponseEntity.ok(trajets);
    }

    // Accepter une réservation
    @PutMapping("/reservation/{reservationId}/accepter")
    public ResponseEntity<Reservation> accepterReservation(@PathVariable int reservationId) {
        Reservation reservation = conducteurService.accepterReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }

    // Répondre à une réclamation
    @PutMapping("/reclamation/{reclamationId}/repondre")
    public ResponseEntity<Reclamation> repondreReclamation(
            @PathVariable int reclamationId, @RequestBody String reponse) {
        Reclamation reclamation = conducteurService.repondreReclamation(reclamationId, reponse);
        return ResponseEntity.ok(reclamation);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> consulterToutesLesReservations() {
        List<Reservation> reservations = conducteurService.consulterToutesLesReservations();
        return ResponseEntity.ok(reservations);
    }
    // Obtenir la liste des véhicules du conducteur
  /*  @GetMapping("/{conducteurId}/vehicules")
    public ResponseEntity<List<Vehicule>> obtenirVehicules(@PathVariable Long conducteurId) {
        List<Vehicule> vehicules = conducteurService.obtenirVehicules(conducteurId);
        return ResponseEntity.ok(vehicules);
    }*/
}