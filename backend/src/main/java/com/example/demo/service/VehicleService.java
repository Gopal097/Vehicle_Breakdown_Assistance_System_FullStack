package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Vehicle;

public interface VehicleService {

    // Save Vehicle
    Vehicle saveVehicle(Vehicle vehicle);

    // Vehicles of the logged-in user
    List<Vehicle> getMyVehicles(String email);

    // Save a vehicle for the logged-in user (owner is taken from the token)
    Vehicle saveVehicleForUser(Vehicle vehicle, String email);

    // Delete a vehicle only if it belongs to the logged-in user
    void deleteMyVehicle(Integer id, String email);

    // Get All Vehicles
    List<Vehicle> getAllVehicles();

    // Pagination
    Page<Vehicle> getAllVehicles(Pageable pageable);

    // Sorting
    List<Vehicle> getAllVehicles(Sort sort);

    // Get Vehicle By ID
    Vehicle getVehicleById(Integer id);

    // Update Vehicle
    Vehicle updateVehicle(Integer id, Vehicle vehicle);

    // Delete Vehicle
    void deleteVehicle(Integer id);

    // Search by Brand
    List<Vehicle> searchByBrand(String brand);

    // Search by Vehicle Type
    List<Vehicle> searchByVehicleType(String vehicleType);

    // Search by Vehicle Number
    List<Vehicle> searchByVehicleNumber(String vehicleNumber);
}