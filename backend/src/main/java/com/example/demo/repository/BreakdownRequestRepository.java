package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;

public interface BreakdownRequestRepository extends JpaRepository<BreakdownRequest, Integer> {

    // Requests raised by the owner of the vehicle (newest first)
    List<BreakdownRequest> findByVehicleUserEmailOrderByIdDesc(String email);

    // Requests assigned to a mechanic (newest first)
    List<BreakdownRequest> findByMechanicEmailOrderByIdDesc(String email);

    List<BreakdownRequest> findByMechanic(Mechanic mechanic);
}
