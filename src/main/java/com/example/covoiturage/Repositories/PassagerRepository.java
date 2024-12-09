package com.example.covoiturage.Repositories;
import com.example.covoiturage.entities.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, Integer> {
    Optional<Passager> findByEmail(String email);
}
