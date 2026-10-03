package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;

public interface MechanicService {

    Mechanic saveMechanic(Mechanic mechanic);

    List<Mechanic> getAllMechanics();

    Mechanic getMechanicById(Integer id);

    Mechanic updateMechanic(Integer id, Mechanic mechanic);

    void deleteMechanic(Integer id);

    // Profile of the logged-in mechanic
    Mechanic getByEmail(String email);

    Mechanic setAvailability(String email, boolean available);

    // Mechanic moves one of his own requests to In Progress / Resolved
    BreakdownRequest updateAssignedStatus(String email, Integer requestId, String status);
}