package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.BreakdownRequest;

public interface BreakdownRequestService {

    BreakdownRequest saveRequest(BreakdownRequest request);

    // Create a request for one of the logged-in user's vehicles
    BreakdownRequest saveRequestForUser(BreakdownRequest request, String email);

    // Requests raised by the logged-in user
    List<BreakdownRequest> getMyRequests(String email);

    // Requests assigned to the logged-in mechanic
    List<BreakdownRequest> getMechanicRequests(String email);

    List<BreakdownRequest> getAllRequests();

    BreakdownRequest getRequestById(Integer id);

    BreakdownRequest updateRequest(Integer id, BreakdownRequest request);

    void deleteRequest(Integer id);
}