package fr.springboot.refuge.security.dao;

import fr.springboot.refuge.security.entity.ERole;
import fr.springboot.refuge.security.entity.Role;

import java.util.Optional;

public interface RoleDAO {
    Optional<Role> findByName(ERole name);
}
