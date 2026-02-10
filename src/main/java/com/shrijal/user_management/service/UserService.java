package com.shrijal.user_management.service;

import com.shrijal.user_management.model.User;
import com.shrijal.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository repo;

    public User createUser(User user) {
        User saved = repo.save(user);
        emailService.sendUserCreatedEmail(saved);
        return saved;
    }


    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repo.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setFirstName(updatedUser.getFirstName());
        existing.setLastName(updatedUser.getLastName());
        existing.setEmail(updatedUser.getEmail());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        existing.setDepartment(updatedUser.getDepartment());
        existing.setRole(updatedUser.getRole());
        existing.setActive(updatedUser.isActive());

        User saved = repo.save(existing);
        emailService.sendUserUpdatedEmail(saved);
        return saved;
    }

    public void deleteUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        repo.deleteById(id);

        emailService.sendUserDeletedEmail(user);
    }


}
