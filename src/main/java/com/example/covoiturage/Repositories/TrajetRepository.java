package com.example.covoiturage.Repositories;
import com.example.covoiturage.entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Integer> {
    List<Trajet> findByVilleDepartAndVilleArriveeAndDateDepart(String villeDepart, String villeArrivee, String dateDepart);
    List<Trajet> findByConducteur_Id(int conducteurId);
}
