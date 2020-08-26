package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.AdoptiveFamily;
import fr.springboot.refuge.services.AdoptiveFamilyService;
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
