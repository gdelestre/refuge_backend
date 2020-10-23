package fr.springboot.refuge.services;

import fr.springboot.refuge.dao.AdoptAnimalDAO;
import fr.springboot.refuge.entity.AdoptAnimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdoptAnimalServiceImpl implements AdoptAnimalService {

    private AdoptAnimalDAO adoptAnimalDAO;

    @Autowired
    public AdoptAnimalServiceImpl(AdoptAnimalDAO adoptAnimalDAO){
        this.adoptAnimalDAO = adoptAnimalDAO;
    }

    @Transactional
    public List<AdoptAnimal> findByAdoptiveFamilyId(int id) {
        return adoptAnimalDAO.findByAdoptiveFamilyId(id);
    }

    @Transactional
    public List<AdoptAnimal> findAll() {
        return adoptAnimalDAO.findAll();
    }

    @Transactional
    public AdoptAnimal findByAnimalId(int id) {
        return adoptAnimalDAO.findByAnimalId(id);
    }

    @Transactional
    public void saveOrUpdate(AdoptAnimal adoptAnimal) {
        adoptAnimalDAO.saveOrUpdate(adoptAnimal);
    }

    @Transactional
    public void deleteByAnimalId(int id) {
        adoptAnimalDAO.deleteByAnimalId(id);
    }
}
