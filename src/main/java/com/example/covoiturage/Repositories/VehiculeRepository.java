package com.example.covoiturage.Repositories;
import com.example.covoiturage.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
  //  List<Vehicule> findByConducteur_Id(Long conducteurId);
}
