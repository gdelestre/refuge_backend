package fr.springboot.refuge.services;

import fr.springboot.refuge.dao.AdoptiveFamilyDAO;
import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.entity.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdoptiveFamilyServiceImpl implements AdoptiveFamilyService {

    private AdoptiveFamilyDAO adoptiveFamilyDAO;

    @Autowired
    public AdoptiveFamilyServiceImpl(AdoptiveFamilyDAO adoptiveFamilyDAO){
        this.adoptiveFamilyDAO = adoptiveFamilyDAO;
    }

    @Transactional
    public List<AdoptiveFamily> findAll() {
        return adoptiveFamilyDAO.findAll();
    }

    @Transactional
    public List<Animal> findAnimalsByAdoptiveFamily(int id) {
        return adoptiveFamilyDAO.findAnimalsByAdoptiveFamily(id);
    }

    @Transactional
    public AdoptiveFamily findById(int id) {
        return adoptiveFamilyDAO.findById(id);
    }

    @Transactional
    public void saveOrUpdate(AdoptiveFamily adoptiveFamily) {
        adoptiveFamilyDAO.saveOrUpdate(adoptiveFamily);
    }

    @Transactional
    public void deleteById(int id) {
        adoptiveFamilyDAO.deleteById(id);
    }
}
