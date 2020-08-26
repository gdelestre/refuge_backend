package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.VeterinaryCare;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AnimalDAOImpl implements AnimalDAO {
    private EntityManager entityManager;

    @Autowired
    public AnimalDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<Animal> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal", Animal.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findWithHostFamily() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.hostFamily is not null", Animal.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findAdopted() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.adoptiveFamily is not null", Animal.class);
        return query.getResultList();
    }

    public Animal findById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return currentSession.get(Animal.class, id);
    }


    public void saveOrUpdate(Animal animal) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(animal);
    }

    public void deleteById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from Animal where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
