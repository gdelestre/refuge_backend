package fr.springboot.refuge.security.services;

import fr.springboot.refuge.security.entity.ERole;
import fr.springboot.refuge.security.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(ERole name);
}
