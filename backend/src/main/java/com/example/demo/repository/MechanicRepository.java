package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Mechanic;

public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {

    Optional<Mechanic> findByEmail(String email);

}