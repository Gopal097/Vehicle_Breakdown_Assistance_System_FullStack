package com.example.demo.util;

import com.example.demo.dto.BreakdownRequestDTO;
import com.example.demo.dto.MechanicDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.VehicleDTO;
import com.example.demo.entity.BreakdownRequest;
import com.example.demo.entity.Mechanic;
import com.example.demo.entity.User;
import com.example.demo.entity.Vehicle;

public class EntityDtoMapper {

    // User Entity -> UserDTO
    public static UserDTO convertToUserDTO(User user) {

        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());

        return dto;
    }

    // Vehicle Entity -> VehicleDTO
    public static VehicleDTO convertToVehicleDTO(Vehicle vehicle) {

        VehicleDTO dto = new VehicleDTO();

        dto.setId(vehicle.getId());
        dto.setVehicleNumber(vehicle.getVehicleNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setFuelType(vehicle.getFuelType());

        return dto;
    }

    // Mechanic Entity -> MechanicDTO
    public static MechanicDTO convertToMechanicDTO(Mechanic mechanic) {

        MechanicDTO dto = new MechanicDTO();

        dto.setId(mechanic.getId());
        dto.setName(mechanic.getName());
        dto.setPhone(mechanic.getPhone());
        dto.setExperience(mechanic.getExperience());
        dto.setGarageName(mechanic.getGarageName());
        dto.setLocation(mechanic.getLocation());
        dto.setAvailability(mechanic.getAvailability());

        return dto;
    }

    // BreakdownRequest Entity -> BreakdownRequestDTO
    public static BreakdownRequestDTO convertToBreakdownRequestDTO(BreakdownRequest request) {

        BreakdownRequestDTO dto = new BreakdownRequestDTO();

        dto.setId(request.getId());
        dto.setProblemType(request.getProblemType());
        dto.setDescription(request.getDescription());
        dto.setLocation(request.getLocation());
        dto.setStatus(request.getStatus());
        dto.setRequestDate(request.getRequestDate());

        return dto;
    }
}