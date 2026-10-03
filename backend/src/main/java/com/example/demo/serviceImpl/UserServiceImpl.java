package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Save User
    @Override
    public User saveUser(User user) {

        // Check if email already exists
        if (repo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        // Public registration can only create normal users
        user.setId(null);
        user.setRole("USER");

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return repo.save(user);
    }

    // Get All Users
    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // Get User By ID
    @Override
    public User getUserById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID : " + id));
    }

    // Update User
    @Override
    public User updateUser(Integer id, User user) {

        User oldUser = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID : " + id));

        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());

        // Encrypt updated password
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));

        oldUser.setPhone(user.getPhone());

        return repo.save(oldUser);
    }

    // Delete User
    @Override
    public void deleteUser(Integer id) {

        User user = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with ID : " + id));

        if ("ADMIN".equals(user.getRole())) {
            throw new IllegalArgumentException("The admin account cannot be deleted");
        }
        if ("MECHANIC".equals(user.getRole())) {
            throw new IllegalArgumentException("Delete mechanics from the Mechanics tab");
        }

        repo.delete(user);
    }
}