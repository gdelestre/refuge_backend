package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Veterinary;
import fr.springboot.refuge.entity.VeterinaryCare;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class VeterinaryDAOImpl implements VeterinaryDAO {
    private EntityManager entityManager;

    @Autowired
    public VeterinaryDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List<Veterinary> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<Veterinary> query = currentSession.createQuery("from Veterinary", Veterinary.class);
        return query.getResultList();
    }

    public Veterinary findById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return currentSession.get(Veterinary.class, id);
    }


    public void saveOrUpdate(Veterinary veterinary) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(veterinary);

    }

    public void deleteById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from Veterinary where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }
}
