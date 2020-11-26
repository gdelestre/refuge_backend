package fr.springboot.refuge.security.dao;

import fr.springboot.refuge.security.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO{

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from User u where u.username = ?1", User.class);
        query.setParameter(1, username);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Boolean existsByUsername(String username) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from User u where u.username = ?1", User.class);
        query.setParameter(1, username);
        return query.setMaxResults(1).uniqueResult() != null;
    }

    @Override
    public void save(User user) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(user);
    }
}
