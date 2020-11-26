package fr.springboot.refuge.security.services;

import fr.springboot.refuge.security.dao.RoleDAO;
import fr.springboot.refuge.security.entity.ERole;
import fr.springboot.refuge.security.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Transactional
    public Optional<Role> findByName(ERole name) {
        return roleDAO.findByName(name);
    }
}
