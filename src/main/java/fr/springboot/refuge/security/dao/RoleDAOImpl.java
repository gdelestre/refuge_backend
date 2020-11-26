package fr.springboot.refuge.security.dao;

import fr.springboot.refuge.security.entity.ERole;
import fr.springboot.refuge.security.entity.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Role> findByName(ERole name) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Role> query = currentSession.createQuery("from Role r where r.name = ?1", Role.class);
        query.setParameter(1, name);
        return Optional.ofNullable(query.getSingleResult());
    }
}
