package com.example.covoiturage.Repositories;
import com.example.covoiturage.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByTrajet_Id(Long trajetId);
    List<Reservation> findByPassager_Id(Long passagerId);
}

