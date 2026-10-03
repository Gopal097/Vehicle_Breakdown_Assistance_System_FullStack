package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Vehicle;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.VehicleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService service;

    // Save Vehicle
    @PostMapping
    public ApiResponse<Vehicle> saveVehicle(@Valid @RequestBody Vehicle vehicle,
                                            Principal principal) {

        Vehicle savedVehicle = service.saveVehicleForUser(vehicle, principal.getName());

        return new ApiResponse<>(
                true,
                "Vehicle Saved Successfully",
                savedVehicle);
    }

    // Vehicles of the logged-in user
    @GetMapping("/my")
    public ApiResponse<List<Vehicle>> getMyVehicles(Principal principal) {

        return new ApiResponse<>(
                true,
                "Your Vehicles Fetched Successfully",
                service.getMyVehicles(principal.getName()));
    }

    // Get All Vehicles
    @GetMapping
    public ApiResponse<List<Vehicle>> getAllVehicles() {

        return new ApiResponse<>(
                true,
                "Vehicle List Fetched Successfully",
                service.getAllVehicles());
    }

    // Get Vehicle By ID
    @GetMapping("/{id}")
    public ApiResponse<Vehicle> getVehicle(@PathVariable Integer id) {

        return new ApiResponse<>(
                true,
                "Vehicle Found",
                service.getVehicleById(id));
    }

    // Update Vehicle
    @PutMapping("/{id}")
    public ApiResponse<Vehicle> updateVehicle(
            @PathVariable Integer id,
            @Valid @RequestBody Vehicle vehicle) {

        return new ApiResponse<>(
                true,
                "Vehicle Updated Successfully",
                service.updateVehicle(id, vehicle));
    }

    // Delete Vehicle
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteVehicle(@PathVariable Integer id, Principal principal) {

        service.deleteMyVehicle(id, principal.getName());

        return new ApiResponse<>(
                true,
                "Vehicle Deleted Successfully",
                null);
    }

    // Pagination
    @GetMapping("/page")
    public ApiResponse<Page<Vehicle>> getVehiclesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Vehicle> vehicles = service.getAllVehicles(PageRequest.of(page, size));

        return new ApiResponse<>(
                true,
                "Vehicle Page Retrieved Successfully",
                vehicles);
    }

    // Sorting
    @GetMapping("/sort")
    public ApiResponse<List<Vehicle>> sortVehicles(
            @RequestParam(defaultValue = "brand") String field,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();

        return new ApiResponse<>(
                true,
                "Vehicles Sorted Successfully",
                service.getAllVehicles(sort));
    }

    // Search by Brand
    @GetMapping("/search/brand")
    public ApiResponse<List<Vehicle>> searchByBrand(
            @RequestParam String brand) {

        return new ApiResponse<>(
                true,
                "Vehicle Search By Brand",
                service.searchByBrand(brand));
    }

    // Search by Vehicle Type
    @GetMapping("/search/type")
    public ApiResponse<List<Vehicle>> searchByVehicleType(
            @RequestParam String vehicleType) {

        return new ApiResponse<>(
                true,
                "Vehicle Search By Type",
                service.searchByVehicleType(vehicleType));
    }

    // Search by Vehicle Number
    @GetMapping("/search/number")
    public ApiResponse<List<Vehicle>> searchByVehicleNumber(
            @RequestParam String vehicleNumber) {

        return new ApiResponse<>(
                true,
                "Vehicle Search By Number",
                service.searchByVehicleNumber(vehicleNumber));
    }
}