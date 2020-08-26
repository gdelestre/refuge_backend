package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.Veterinary;

import java.util.List;

public interface VeterinaryDAO {
   List<Veterinary> findAll();
   Veterinary findById(int id);
    void saveOrUpdate(Veterinary veterinary);
    void deleteById(int id);
}
