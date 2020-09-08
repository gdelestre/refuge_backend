package fr.springboot.refuge.services;

import fr.springboot.refuge.dao.HostFamilyDAO;
import fr.springboot.refuge.entity.Animal;
import fr.springboot.refuge.entity.HostFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HostFamilyServiceImpl implements HostFamilyService{

    private HostFamilyDAO hostFamilyDAO;

    @Autowired
    public HostFamilyServiceImpl(HostFamilyDAO hostFamilyDAO){
        this.hostFamilyDAO = hostFamilyDAO;
    }


    @Transactional
    public List<HostFamily> findAll() {
        return hostFamilyDAO.findAll();
    }

    @Transactional
    public List<Animal> findAnimalsByHostFamily(int id) {
        return hostFamilyDAO.findAnimalsByHostFamily(id);
    }

    @Transactional
    public HostFamily findById(int id) {
        return hostFamilyDAO.findById(id);
    }

    @Transactional
    public void saveOrUpdate(HostFamily hostFamily) {
        hostFamilyDAO.saveOrUpdate(hostFamily);
    }

    @Transactional
    public void deleteById(int id) {
        hostFamilyDAO.deleteById(id);
    }
}
