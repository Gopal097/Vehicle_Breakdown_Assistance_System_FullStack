package com.example.demo.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.BreakdownRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.BreakdownRequestRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.BreakdownRequestService;

@Service
public class BreakdownRequestServiceImpl implements BreakdownRequestService {

    @Autowired
    private BreakdownRequestRepository repo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Override
    public BreakdownRequest saveRequestForUser(BreakdownRequest request, String email) {
        if (request.getVehicle() == null || request.getVehicle().getId() == null) {
            throw new IllegalArgumentException("Vehicle is required");
        }
        Vehicle vehicle = vehicleRepo.findById(request.getVehicle().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        if (vehicle.getUser() == null || !email.equals(vehicle.getUser().getEmail())) {
            throw new AccessDeniedException("Not your vehicle");
        }
        request.setId(null);
        request.setVehicle(vehicle);
        request.setMechanic(null);
        return saveRequest(request);
    }

    @Override
    public List<BreakdownRequest> getMyRequests(String email) {
        return repo.findByVehicleUserEmailOrderByIdDesc(email);
    }

    @Override
    public List<BreakdownRequest> getMechanicRequests(String email) {
        return repo.findByMechanicEmailOrderByIdDesc(email);
    }

    @Override
    public BreakdownRequest saveRequest(BreakdownRequest request) {
        request.setRequestDate(LocalDateTime.now());
        request.setStatus("Pending");
        return repo.save(request);
    }

    @Override
    public List<BreakdownRequest> getAllRequests() {
        return repo.findAll();
    }

    @Override
    public BreakdownRequest getRequestById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Breakdown Request not found with ID : " + id));
    }

    @Override
    public BreakdownRequest updateRequest(Integer id, BreakdownRequest request) {

        BreakdownRequest oldRequest = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Breakdown Request not found with ID : " + id));

        oldRequest.setProblemType(request.getProblemType());
        oldRequest.setDescription(request.getDescription());
        oldRequest.setLocation(request.getLocation());
        oldRequest.setStatus(request.getStatus());
        oldRequest.setVehicle(request.getVehicle());
        oldRequest.setMechanic(request.getMechanic());

        return repo.save(oldRequest);
    }

    @Override
    public void deleteRequest(Integer id) {

        BreakdownRequest request = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Breakdown Request not found with ID : " + id));

        repo.delete(request);
    }
}