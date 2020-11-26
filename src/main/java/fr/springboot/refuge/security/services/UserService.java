package fr.springboot.refuge.security.services;

import fr.springboot.refuge.security.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    void save(User user);
}
