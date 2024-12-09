package com.example.covoiturage.Controller;

import com.example.covoiturage.entities.Conducteur;
import com.example.covoiturage.entities.Reclamation;
import com.example.covoiturage.Services.AdminService;
import com.example.covoiturage.entities.Trajet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

    private  AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Valider le compte d'un conducteur
    @PutMapping("/conducteur/{conducteurId}/valider")
    public ResponseEntity<Conducteur> validerConducteur(@PathVariable int conducteurId) {
        try {
            Conducteur conducteur = adminService.validerConducteur(conducteurId);
            return ResponseEntity.ok(conducteur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Consulter les réclamations
    @GetMapping("/reclamations")
    public ResponseEntity<List<Reclamation>> consulterReclamations() {
        List<Reclamation> reclamations = adminService.consulterReclamations();
        return ResponseEntity.ok(reclamations);
    }

    // Générer des statistiques sur les conducteurs
    @GetMapping("/conducteurs/statistiques")
    public ResponseEntity<List<Conducteur>> genererStatistiquesConducteurs() {
        List<Conducteur> conducteurs = adminService.genererStatistiquesConducteurs();
        return ResponseEntity.ok(conducteurs);
    }
    @GetMapping("/{conducteurId}/trajets")
    public ResponseEntity<List<Trajet>> consulterTrajets(@PathVariable int conducteurId) {
        List<Trajet> trajets = adminService.consulterTrajets(conducteurId);
        return ResponseEntity.ok(trajets);
    }
}