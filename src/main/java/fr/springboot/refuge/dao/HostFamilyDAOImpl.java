package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


@Repository
public class HostFamilyDAOImpl implements HostFamilyDAO {

    private EntityManager entityManager;

    @Autowired
    public HostFamilyDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public List<HostFamily> findFreeFamilies() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<HostFamily> query = currentSession.createQuery("from HostFamily family where family.free = 1 order by family.lastName", HostFamily.class);
        return query.getResultList();
    }

    @Override
    public List<HostFamily> findFullFamilies() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<HostFamily> query = currentSession.createQuery("from HostFamily family where family.free = 0 order by family.lastName", HostFamily.class);
        return query.getResultList();
    }

    @Override
    public HostFamily findByPhoneNumber(String phoneNumber) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<HostFamily> query = currentSession.createQuery("from HostFamily family where family.phoneNumber = ?1", HostFamily.class);
        query.setParameter(1, phoneNumber);
        try{
            return query.getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }

    @Override
    public List<Animal> findAnimalsByHostFamily(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("from Animal a where a.hostFamily.id =?1", Animal.class);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public HostFamily findById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return currentSession.get(HostFamily.class, id);
    }

    @Override
    public void saveOrUpdate(HostFamily hostFamily) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(hostFamily);
    }

    @Override
    public void deleteById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from HostFamily where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
