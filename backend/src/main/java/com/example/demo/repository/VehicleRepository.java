package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    // Search Vehicle by Brand
    List<Vehicle> findByBrand(String brand);

    // Search Vehicle by Vehicle Type
    List<Vehicle> findByVehicleType(String vehicleType);

    // Search Vehicle by Vehicle Number
    List<Vehicle> findByVehicleNumber(String vehicleNumber);

    // Vehicles owned by the user with this email
    List<Vehicle> findByUserEmail(String email);

}