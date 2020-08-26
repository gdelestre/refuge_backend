package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.Veterinary;

import java.util.List;

public interface VeterinaryService {
    List<Veterinary> findAll();
    Veterinary findById(int id);
    void saveOrUpdate(Veterinary veterinary);
    void deleteById(int id);
}
