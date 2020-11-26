package fr.springboot.refuge.security.services;

import fr.springboot.refuge.security.dao.UserDAO;
import fr.springboot.refuge.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Transactional
    public Boolean existsByUsername(String username) {
        return userDAO.existsByUsername(username);
    }

    @Transactional
    public void save(User user) {
        userDAO.save(user);
    }
}
