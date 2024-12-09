package com.example.covoiturage.Controller;

import com.example.covoiturage.entities.Reclamation;
import com.example.covoiturage.entities.Reservation;
import com.example.covoiturage.entities.Trajet;
import com.example.covoiturage.Services.PassagerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passager")

public class PassagerController {


    private final PassagerService passagerService;

    // Constructeur explicite pour l'injection des dépendances
    @Autowired
    public PassagerController(PassagerService passagerService) {
        this.passagerService = passagerService;
    }

    // Rechercher des trajets disponibles

    @GetMapping("/recherche")
    public ResponseEntity<List<Trajet>> rechercherTrajets(
            @RequestParam String villeDepart,
            @RequestParam String villeArrivee,
            @RequestParam String dateDepart) {

        List<Trajet> trajets = passagerService.rechercherTrajets(villeDepart, villeArrivee, dateDepart);
        return ResponseEntity.ok(trajets);
    }

    // Réserver un trajet
    @PostMapping("/reservation")
    public ResponseEntity<Reservation> reserverTrajet(
            @RequestParam int trajetId,
            @RequestParam int passagerId) {

        try {
            Reservation reservation = passagerService.reserverTrajet(trajetId, passagerId);
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Déposer une réclamation
    @PostMapping("/reclamation")
    public ResponseEntity<Reclamation> deposerReclamation(
            @RequestParam int passagerId,
            @RequestParam int conducteurId,
            @RequestParam String objet,
            @RequestParam String description) {

        Reclamation reclamation = passagerService.deposerReclamation(passagerId, conducteurId, objet, description);
        return ResponseEntity.ok(reclamation);
    }
}