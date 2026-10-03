package com.example.demo.dto;

public class MechanicDTO {

    private Integer id;

    private String name;

    private String phone;

    private Integer experience;

    private String garageName;

    private String location;

    private Boolean availability;

    public MechanicDTO() {
    }

    public MechanicDTO(Integer id, String name, String phone,
                       Integer experience, String garageName,
                       String location, Boolean availability) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.experience = experience;
        this.garageName = garageName;
        this.location = location;
        this.availability = availability;
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
}