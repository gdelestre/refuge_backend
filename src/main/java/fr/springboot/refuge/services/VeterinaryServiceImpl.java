package fr.springboot.refuge.services;

import fr.springboot.refuge.dao.VeterinaryDAO;
import fr.springboot.refuge.entity.Veterinary;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VeterinaryServiceImpl implements VeterinaryService {
    private VeterinaryDAO veterinaryDAO;

    public VeterinaryServiceImpl(VeterinaryDAO veterinaryDAO){
        this.veterinaryDAO = veterinaryDAO;
    }

    @Transactional
    public List<Veterinary> findAll() {
        return veterinaryDAO.findAll();
    }

    @Transactional
    public Veterinary findById(int id) {
        return veterinaryDAO.findById(id);
    }

    @Transactional
    public void saveOrUpdate(Veterinary veterinary) {
        veterinaryDAO.saveOrUpdate(veterinary);
    }

    @Transactional
    public void deleteById(int id) {
        veterinaryDAO.deleteById(id);
    }
}
