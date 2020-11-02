package fr.springboot.refuge.services;

import fr.springboot.refuge.dao.AnimalDAO;
import fr.springboot.refuge.entity.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService{
    private AnimalDAO animalDAO;

    @Autowired
    public AnimalServiceImpl(AnimalDAO animalDAO){
        this.animalDAO = animalDAO;
    }

    @Transactional
    public List<Animal> findAll() {
        return this.animalDAO.findAll();
    }

    @Transactional
    public List<Animal> findWithHostFamily() {
        return this.animalDAO.findWithHostFamily();
    }

    @Transactional
    public List<Animal> findAdopted() {
        return this.animalDAO.findAdopted();
    }

    @Transactional
    public List<Animal> findBySpecies(String species) {
        return animalDAO.findBySpecies(species);
    }

    @Transactional
    public Animal findById(int id) {
        return this.animalDAO.findById(id);
    }

    @Transactional
    public Animal findByName(String name) {
        return this.animalDAO.findByName(name);
    }

    @Transactional
    public void saveOrUpdate(Animal animal) {
        this.animalDAO.saveOrUpdate(animal);
    }

    @Transactional
    public void deleteById(int id) {
        this.animalDAO.deleteById(id);
    }
}
