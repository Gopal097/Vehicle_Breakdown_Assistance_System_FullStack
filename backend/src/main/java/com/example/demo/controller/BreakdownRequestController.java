package com.example.demo.controller;

import java.security.Principal;
import java.util.List;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.BreakdownRequest;
import com.example.demo.service.BreakdownRequestService;

@RestController
@RequestMapping("/requests")
public class BreakdownRequestController {

    @Autowired
    private BreakdownRequestService service;

    @PostMapping
    public BreakdownRequest save(@Valid @RequestBody BreakdownRequest request,
                                 Principal principal) {
        return service.saveRequestForUser(request, principal.getName());
    }

    // Requests of the logged-in user
    @GetMapping("/my")
    public List<BreakdownRequest> getMyRequests(Principal principal) {
        return service.getMyRequests(principal.getName());
    }

    @GetMapping
    public List<BreakdownRequest> getAll() {
        return service.getAllRequests();
    }

    @GetMapping("/{id}")
    public BreakdownRequest getById(@PathVariable Integer id) {
        return service.getRequestById(id);
    }

    @PutMapping("/{id}")
    public BreakdownRequest update(@PathVariable Integer id,
                                   @RequestBody BreakdownRequest request) {
        return service.updateRequest(id, request);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.deleteRequest(id);
        return "Breakdown Request Deleted Successfully";
    }
}