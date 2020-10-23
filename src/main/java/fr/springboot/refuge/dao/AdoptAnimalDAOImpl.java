package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.AdoptAnimal;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class AdoptAnimalDAOImpl implements AdoptAnimalDAO {

    private EntityManager entityManager;

    @Autowired
    public  AdoptAnimalDAOImpl(EntityManager entityManager){
        this.entityManager =entityManager;
    }

    @Override
    public AdoptAnimal findByAnimalId(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("from AdoptAnimal a where a.adoptedAnimal.id =?1");
        query.setParameter(1, id);
        return (AdoptAnimal) query.getSingleResult();

    }

    @Override
    public List<AdoptAnimal> findByAdoptiveFamilyId(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<AdoptAnimal> query = currentSession.createQuery("from AdoptAnimal a where a.adoptiveFamily.id =?1");
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public List<AdoptAnimal> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<AdoptAnimal> query = currentSession.createQuery("from AdoptAnimal Order By adoptionDate DESC", AdoptAnimal.class);
        return query.getResultList();
    }

    @Override
    public void saveOrUpdate(AdoptAnimal adoptAnimal) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(adoptAnimal);
    }

    @Override
    public void deleteByAnimalId(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createNativeQuery("delete from adopt_animal where id_animal=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
