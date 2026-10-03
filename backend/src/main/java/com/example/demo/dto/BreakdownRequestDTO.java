package com.example.demo.dto;

import java.time.LocalDateTime;

public class BreakdownRequestDTO {

    private Integer id;

    private String problemType;

    private String description;

    private String location;

    private String status;

    private LocalDateTime requestDate;

    public BreakdownRequestDTO() {
    }

    public BreakdownRequestDTO(Integer id, String problemType,
                               String description, String location,
                               String status,
                               LocalDateTime requestDate) {
        this.id = id;
        this.problemType = problemType;
        this.description = description;
        this.location = location;
        this.status = status;
        this.requestDate = requestDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }
}