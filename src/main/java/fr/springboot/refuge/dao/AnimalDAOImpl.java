package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.Species;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class AnimalDAOImpl implements AnimalDAO {
    private EntityManager entityManager;

    @Autowired
    public AnimalDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<Animal> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a Order By a.name", Animal.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findWithHostFamily() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.hostFamily is not null Order By a.name", Animal.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findAdopted() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.isAdopted = 1 Order By a.name", Animal.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findBySpecies(String species) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.species =?1 Order By a.name", Animal.class);
        Species param = Species.valueOf(species);
        query.setParameter(1, param);
        return query.getResultList();
    }

    @Override
    public Animal findById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return currentSession.get(Animal.class, id);
    }

    @Override
    public Animal findByName(String name) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.name = ?1", Animal.class);
        query.setParameter(1, name);
        try{
             return query.getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public void saveOrUpdate(Animal animal) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(animal);
    }

    @Override
    public void deleteById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from Animal where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
