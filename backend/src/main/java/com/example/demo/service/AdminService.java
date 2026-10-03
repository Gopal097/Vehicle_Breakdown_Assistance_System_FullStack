package com.example.demo.service;

import com.example.demo.dto.MechanicAccountRequest;
import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;

public interface AdminService {

    // Assign a mechanic to a breakdown request
    BreakdownRequest assignMechanic(Integer requestId, Integer mechanicId);

    // Update the status of a breakdown request
    BreakdownRequest updateStatus(Integer requestId, String status);

    // Create a mechanic together with a MECHANIC login account
    Mechanic createMechanicWithAccount(MechanicAccountRequest request);

}