package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MechanicAccountRequest;
import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;
import com.example.demo.service.AdminService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService service;

    @PutMapping("/assign/{requestId}/{mechanicId}")
    public BreakdownRequest assignMechanic(
            @PathVariable Integer requestId,
            @PathVariable Integer mechanicId) {

        return service.assignMechanic(requestId, mechanicId);
    }

    @PutMapping("/status/{requestId}")
    public BreakdownRequest updateStatus(
            @PathVariable Integer requestId,
            @RequestParam String status) {

        return service.updateStatus(requestId, status);
    }

    // Create a mechanic with a login account
    @PostMapping("/mechanics")
    public Mechanic createMechanic(@Valid @RequestBody MechanicAccountRequest request) {
        return service.createMechanicWithAccount(request);
    }
}
