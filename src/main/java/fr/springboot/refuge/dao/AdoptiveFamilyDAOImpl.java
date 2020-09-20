package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AdoptiveFamilyDAOImpl implements AdoptiveFamilyDAO {

    private EntityManager entityManager;

    @Autowired
    public AdoptiveFamilyDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<AdoptiveFamily> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<AdoptiveFamily> query = currentSession.createQuery("from AdoptiveFamily", AdoptiveFamily.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findAnimalsByAdoptiveFamily(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("select a from Animal a where id_adoptive_family =?1");
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public AdoptiveFamily findById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return currentSession.get(AdoptiveFamily.class, id);
    }

    @Override
    public void saveOrUpdate(AdoptiveFamily adoptiveFamily) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(adoptiveFamily);
    }

    @Override
    public void deleteById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from AdoptiveFamily where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
