package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "mechanics")
public class Mechanic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Mechanic Name is required")
    private String name;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must contain exactly 10 digits")
    private String phone;

    @NotNull(message = "Experience is required")
    private Integer experience;

    @NotBlank(message = "Garage Name is required")
    private String garageName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Availability is required")
    private Boolean availability;

    // Email of the linked MECHANIC login account
    private String email;

    @OneToMany(mappedBy = "mechanic")
    @JsonIgnore
    private List<BreakdownRequest> requests;

    public Mechanic() {
    }

    public Mechanic(Integer id, String name, String phone,
                    Integer experience, String garageName,
                    String location, Boolean availability,
                    List<BreakdownRequest> requests) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.experience = experience;
        this.garageName = garageName;
        this.location = location;
        this.availability = availability;
        this.requests = requests;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getGarageName() {
        return garageName;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public List<BreakdownRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<BreakdownRequest> requests) {
        this.requests = requests;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
