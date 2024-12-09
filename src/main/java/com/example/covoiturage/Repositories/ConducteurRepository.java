package com.example.covoiturage.Repositories;
import com.example.covoiturage.entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Integer> {
    Optional<Conducteur> findByEmail(String email);
    Optional<Conducteur> findById(int id);
}
