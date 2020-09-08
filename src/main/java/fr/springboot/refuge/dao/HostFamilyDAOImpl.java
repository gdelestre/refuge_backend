package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;
import fr.springboot.refuge.entity.VeterinaryCare;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class HostFamilyDAOImpl implements HostFamilyDAO {

    private EntityManager entityManager;

    @Autowired
    public HostFamilyDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public List<HostFamily> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<HostFamily> query = currentSession.createQuery("from HostFamily", HostFamily.class);
        return query.getResultList();
    }

    @Override
    public List<Animal> findAnimalsByHostFamily(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Animal> query = currentSession.createQuery("select a from Animal a where id_host_family =?1");
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
