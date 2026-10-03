package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Mechanic;
import com.example.demo.entity.User;
import com.example.demo.repository.MechanicRepository;
import com.example.demo.repository.UserRepository;

/**
 * Runs at start-up: makes sure an admin exists and, on an empty database,
 * creates a few demo mechanics (each with a login account).
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MechanicRepository mechanicRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {

        // Users created before roles existed
        userRepo.findAll().forEach(u -> {
            if (u.getRole() == null) {
                u.setRole("USER");
                userRepo.save(u);
            }
        });

        if (!userRepo.existsByEmail(adminEmail)) {
            createUser("Administrator", adminEmail, "9000000000", adminPassword, "ADMIN");
        }

        if (mechanicRepo.count() == 0) {
            createMechanic("Ravi Kumar", "9876543210", 8, "Kumar Auto Works", "Indiranagar", "ravi@vehiclebreakdown.com");
            createMechanic("Suresh Gowda", "9876543211", 12, "Gowda Motors", "Whitefield", "suresh@vehiclebreakdown.com");
            createMechanic("Imran Khan", "9876543212", 5, "Speedfix Garage", "Koramangala", "imran@vehiclebreakdown.com");
            createMechanic("Anitha Rao", "9876543213", 10, "Rao Roadside Care", "Jayanagar", "anitha@vehiclebreakdown.com");
        }
    }

    private void createUser(String name, String email, String phone, String rawPassword, String role) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPhone(phone);
        u.setPassword(encoder.encode(rawPassword));
        u.setRole(role);
        userRepo.save(u);
    }

    private void createMechanic(String name, String phone, int experience,
                                String garage, String location, String email) {
        if (!userRepo.existsByEmail(email)) {
            createUser(name, email, phone, "Mechanic@123", "MECHANIC");
        }
        Mechanic m = new Mechanic();
        m.setName(name);
        m.setPhone(phone);
        m.setExperience(experience);
        m.setGarageName(garage);
        m.setLocation(location);
        m.setAvailability(true);
        m.setEmail(email);
        mechanicRepo.save(m);
    }
}
