package fr.springboot.refuge.services;

import fr.springboot.refuge.dao.VeterinaryCareDAO;
import fr.springboot.refuge.entity.VeterinaryCare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VeterinaryCareServiceImpl implements VeterinaryCareService{

    private VeterinaryCareDAO veterinaryCareDAO;

    @Autowired
    public VeterinaryCareServiceImpl(VeterinaryCareDAO veterinaryCareDAO){
        this.veterinaryCareDAO = veterinaryCareDAO;
    }

    @Transactional
    public List<VeterinaryCare> findAllByAnimalId(int id) {
        return veterinaryCareDAO.findAllByAnimalId(id);
    }

    @Transactional
    public List<VeterinaryCare> findAll() {
        return veterinaryCareDAO.findAll();
    }

    @Transactional
    public List<VeterinaryCare> findCaresToDo() {
        return veterinaryCareDAO.findCaresToDo();
    }

    @Transactional
    public VeterinaryCare findById(int id) {
        return veterinaryCareDAO.findById(id);
    }

    @Transactional
    public void saveOrUpdate(VeterinaryCare veterinaryCare) {
        veterinaryCareDAO.saveOrUpdate(veterinaryCare);
    }

    @Transactional
    public void deleteById(int id) {
        veterinaryCareDAO.deleteById(id);
    }

    @Transactional
    public void deleteAllCaresByAnimalId(int id) {
        veterinaryCareDAO.deleteAllCaresByAnimalId(id);
    }
}
