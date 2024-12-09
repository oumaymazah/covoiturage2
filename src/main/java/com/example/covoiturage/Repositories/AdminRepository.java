package com.example.covoiturage.Repositories;

import com.example.covoiturage.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
