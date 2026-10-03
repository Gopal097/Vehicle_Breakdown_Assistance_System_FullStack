package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;
import com.example.demo.service.BreakdownRequestService;
import com.example.demo.service.MechanicService;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {

    @Autowired
    private MechanicService service;

    @Autowired
    private BreakdownRequestService requestService;

    @PostMapping
    public Mechanic saveMechanic(@Valid @RequestBody Mechanic mechanic) {
        return service.saveMechanic(mechanic);
    }
    // ---- Logged-in mechanic ----
    @GetMapping("/me")
    public Mechanic me(Principal principal) {
        return service.getByEmail(principal.getName());
    }

    @PutMapping("/me/availability")
    public Mechanic setAvailability(@RequestParam boolean available, Principal principal) {
        return service.setAvailability(principal.getName(), available);
    }

    @GetMapping("/me/requests")
    public List<BreakdownRequest> myRequests(Principal principal) {
        return requestService.getMechanicRequests(principal.getName());
    }

    @PutMapping("/me/requests/{requestId}/status")
    public BreakdownRequest updateMyRequestStatus(@PathVariable Integer requestId,
                                                  @RequestParam String status,
                                                  Principal principal) {
        return service.updateAssignedStatus(principal.getName(), requestId, status);
    }

    @GetMapping
    public List<Mechanic> getAllMechanics() {
        return service.getAllMechanics();
    }

    @GetMapping("/{id}")
    public Mechanic getMechanic(@PathVariable Integer id) {
        return service.getMechanicById(id);
    }

    @PutMapping("/{id}")
    public Mechanic updateMechanic(@PathVariable Integer id,
                                   @RequestBody Mechanic mechanic) {
        return service.updateMechanic(id, mechanic);
    }

    @DeleteMapping("/{id}")
    public String deleteMechanic(@PathVariable Integer id) {
        service.deleteMechanic(id);
        return "Mechanic Deleted Successfully";
    }
}