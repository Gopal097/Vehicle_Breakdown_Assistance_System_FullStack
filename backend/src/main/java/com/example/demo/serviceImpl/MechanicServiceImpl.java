package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BreakdownRequestRepository;
import com.example.demo.repository.MechanicRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MechanicService;

@Service
public class MechanicServiceImpl implements MechanicService {

    @Autowired
    private MechanicRepository repo;

    @Autowired
    private BreakdownRequestRepository requestRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public Mechanic getByEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic profile not found for " + email));
    }

    @Override
    public Mechanic setAvailability(String email, boolean available) {
        Mechanic mechanic = getByEmail(email);
        mechanic.setAvailability(available);
        return repo.save(mechanic);
    }

    @Override
    public BreakdownRequest updateAssignedStatus(String email, Integer requestId, String status) {
        Mechanic mechanic = getByEmail(email);
        BreakdownRequest request = requestRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Breakdown Request not found with ID : " + requestId));

        if (request.getMechanic() == null || !mechanic.getId().equals(request.getMechanic().getId())) {
            throw new AccessDeniedException("Request is not assigned to you");
        }
        if (!"In Progress".equals(status) && !"Resolved".equals(status)) {
            throw new IllegalArgumentException("Mechanics can only set In Progress or Resolved");
        }
        request.setStatus(status);
        return requestRepo.save(request);
    }

    @Override
    public Mechanic saveMechanic(Mechanic mechanic) {
        return repo.save(mechanic);
    }

    @Override
    public List<Mechanic> getAllMechanics() {
        return repo.findAll();
    }

    @Override
    public Mechanic getMechanicById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mechanic not found with ID : " + id));
    }

    @Override
    public Mechanic updateMechanic(Integer id, Mechanic mechanic) {

        Mechanic oldMechanic = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mechanic not found with ID : " + id));

        oldMechanic.setName(mechanic.getName());
        oldMechanic.setPhone(mechanic.getPhone());
        oldMechanic.setExperience(mechanic.getExperience());
        oldMechanic.setGarageName(mechanic.getGarageName());
        oldMechanic.setLocation(mechanic.getLocation());
        oldMechanic.setAvailability(mechanic.getAvailability());

        return repo.save(oldMechanic);
    }

    @Override
    @Transactional
    public void deleteMechanic(Integer id) {

        Mechanic mechanic = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mechanic not found with ID : " + id));

        // Give open jobs back to the pending queue instead of deleting them
        List<BreakdownRequest> assigned = requestRepo.findByMechanic(mechanic);
        for (BreakdownRequest r : assigned) {
            r.setMechanic(null);
            if ("Assigned".equals(r.getStatus()) || "In Progress".equals(r.getStatus())) {
                r.setStatus("Pending");
            }
        }
        requestRepo.saveAll(assigned);

        repo.delete(mechanic);

        // Remove the mechanic's login account as well
        if (mechanic.getEmail() != null) {
            userRepo.findByEmail(mechanic.getEmail()).ifPresent(userRepo::delete);
        }
    }
}