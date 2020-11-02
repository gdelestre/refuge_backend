package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.VeterinaryCare;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Repository
public class VeterinaryCareDAOImpl implements VeterinaryCareDAO{

    private EntityManager entityManager;

    @Autowired
    public VeterinaryCareDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public List<VeterinaryCare> findAllByAnimalId(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<VeterinaryCare> query = currentSession.createQuery("select care from VeterinaryCare care where id_animal =?1 Order By examenDate DESC", VeterinaryCare.class);
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public List<VeterinaryCare> findAll() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<VeterinaryCare> query = currentSession.createQuery("from VeterinaryCare Order By examenDate DESC", VeterinaryCare.class);
        return query.getResultList();
    }

    @Override
    public List<VeterinaryCare> findCaresToDo() {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query<VeterinaryCare> query = currentSession.createQuery("from VeterinaryCare care where care.examenDate >= ?1 Order By examenDate ASC", VeterinaryCare.class);
        Date date = new Date();
        query.setParameter(1, date, TemporalType.DATE );
        return query.getResultList();
    }

    @Override
    public VeterinaryCare findById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        return currentSession.get(VeterinaryCare.class, id);
    }

    @Override
    public void saveOrUpdate(VeterinaryCare veterinaryCare) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(veterinaryCare);
    }

    @Override
    public void deleteById(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("delete from VeterinaryCare where id=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public void deleteAllCaresByAnimalId(int id) {
        Session currentSession = this.entityManager.unwrap(Session.class);
        Query query = currentSession.createNativeQuery("delete from veterinary_care where id_animal=?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

}
