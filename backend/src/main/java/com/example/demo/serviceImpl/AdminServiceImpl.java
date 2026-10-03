package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.MechanicAccountRequest;
import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BreakdownRequestRepository;
import com.example.demo.repository.MechanicRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    private static final List<String> STATUSES =
            List.of("Pending", "Assigned", "In Progress", "Resolved", "Cancelled");

    @Autowired
    private BreakdownRequestRepository requestRepo;

    @Autowired
    private MechanicRepository mechanicRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public BreakdownRequest assignMechanic(Integer requestId, Integer mechanicId) {

        BreakdownRequest request = requestRepo.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Breakdown Request not found with ID : " + requestId));

        Mechanic mechanic = mechanicRepo.findById(mechanicId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Mechanic not found with ID : " + mechanicId));

        if (!Boolean.TRUE.equals(mechanic.getAvailability())) {
            throw new IllegalArgumentException("Mechanic is not available");
        }

        request.setMechanic(mechanic);
        request.setStatus("Assigned");

        return requestRepo.save(request);
    }

    @Override
    public BreakdownRequest updateStatus(Integer requestId, String status) {

        if (!STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status. Allowed: " + String.join(", ", STATUSES));
        }

        BreakdownRequest request = requestRepo.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Breakdown Request not found with ID : " + requestId));

        request.setStatus(status);

        return requestRepo.save(request);
    }

    @Override
    @Transactional
    public Mechanic createMechanicWithAccount(MechanicAccountRequest req) {

        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole("MECHANIC");
        userRepo.save(user);

        Mechanic mechanic = new Mechanic();
        mechanic.setName(req.getName());
        mechanic.setPhone(req.getPhone());
        mechanic.setExperience(req.getExperience());
        mechanic.setGarageName(req.getGarageName());
        mechanic.setLocation(req.getLocation());
        mechanic.setAvailability(true);
        mechanic.setEmail(req.getEmail());

        return mechanicRepo.save(mechanic);
    }
}
