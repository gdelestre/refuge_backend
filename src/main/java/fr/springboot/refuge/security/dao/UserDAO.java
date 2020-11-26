package fr.springboot.refuge.security.dao;

import fr.springboot.refuge.security.entity.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    void save(User user);

}
