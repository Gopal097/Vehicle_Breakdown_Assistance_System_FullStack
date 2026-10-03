package com.example.demo.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.Vehicle;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehicleRepository repo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Vehicle> getMyVehicles(String email) {
        return repo.findByUserEmail(email);
    }

    @Override
    public Vehicle saveVehicleForUser(Vehicle vehicle, String email) {
        User owner = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found : " + email));
        vehicle.setId(null);
        vehicle.setUser(owner);
        return saveVehicle(vehicle);
    }

    @Override
    public void deleteMyVehicle(Integer id, String email) {
        Vehicle vehicle = getVehicleById(id);
        if (vehicle.getUser() == null || !email.equals(vehicle.getUser().getEmail())) {
            throw new AccessDeniedException("Not your vehicle");
        }
        deleteVehicle(id);
    }

    // Save Vehicle
    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {

        logger.info("Saving Vehicle : {}", vehicle.getVehicleNumber());

        Vehicle savedVehicle = repo.save(vehicle);

        logger.info("Vehicle Saved Successfully with ID : {}", savedVehicle.getId());

        return savedVehicle;
    }

    // Get All Vehicles
    @Override
    public List<Vehicle> getAllVehicles() {

        logger.info("Fetching All Vehicles");

        return repo.findAll();
    }

    // Pagination
    @Override
    public Page<Vehicle> getAllVehicles(Pageable pageable) {

        logger.info("Fetching Vehicles with Pagination - Page: {}, Size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        return repo.findAll(pageable);
    }

    // Sorting
    @Override
    public List<Vehicle> getAllVehicles(Sort sort) {

        logger.info("Fetching Vehicles with Sorting");

        return repo.findAll(sort);
    }

    // Get Vehicle By ID
    @Override
    public Vehicle getVehicleById(Integer id) {

        logger.info("Fetching Vehicle with ID : {}", id);

        return repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Vehicle Not Found with ID : {}", id);
                    return new ResourceNotFoundException("Vehicle not found with ID : " + id);
                });
    }

    // Update Vehicle
    @Override
    public Vehicle updateVehicle(Integer id, Vehicle vehicle) {

        logger.info("Updating Vehicle with ID : {}", id);

        Vehicle oldVehicle = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Vehicle Not Found with ID : {}", id);
                    return new ResourceNotFoundException("Vehicle not found with ID : " + id);
                });

        oldVehicle.setVehicleNumber(vehicle.getVehicleNumber());
        oldVehicle.setVehicleType(vehicle.getVehicleType());
        oldVehicle.setBrand(vehicle.getBrand());
        oldVehicle.setModel(vehicle.getModel());
        oldVehicle.setFuelType(vehicle.getFuelType());
        oldVehicle.setUser(vehicle.getUser());

        Vehicle updatedVehicle = repo.save(oldVehicle);

        logger.info("Vehicle Updated Successfully with ID : {}", id);

        return updatedVehicle;
    }

    // Delete Vehicle
    @Override
    public void deleteVehicle(Integer id) {

        logger.info("Deleting Vehicle with ID : {}", id);

        Vehicle vehicle = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Vehicle Not Found with ID : {}", id);
                    return new ResourceNotFoundException("Vehicle not found with ID : " + id);
                });

        repo.delete(vehicle);

        logger.info("Vehicle Deleted Successfully with ID : {}", id);
    }

    // Search by Brand
    @Override
    public List<Vehicle> searchByBrand(String brand) {

        logger.info("Searching Vehicles by Brand : {}", brand);

        return repo.findByBrand(brand);
    }

    // Search by Vehicle Type
    @Override
    public List<Vehicle> searchByVehicleType(String vehicleType) {

        logger.info("Searching Vehicles by Type : {}", vehicleType);

        return repo.findByVehicleType(vehicleType);
    }

    // Search by Vehicle Number
    @Override
    public List<Vehicle> searchByVehicleNumber(String vehicleNumber) {

        logger.info("Searching Vehicle by Number : {}", vehicleNumber);

        return repo.findByVehicleNumber(vehicleNumber);
    }
}