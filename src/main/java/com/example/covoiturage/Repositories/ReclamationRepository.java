package com.example.covoiturage.Repositories;
import com.example.covoiturage.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Integer> {
    List<Reclamation> findByConducteur_Id(Long conducteurId);
    List<Reclamation> findByPassager_Id(Long passagerId);
}
