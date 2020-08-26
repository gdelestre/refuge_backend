package fr.springboot.refuge.services;

import fr.springboot.refuge.entity.HostFamily;

import java.util.List;

public interface HostFamilyService {
    List<HostFamily> findAll();
    HostFamily findById(int id);
    void saveOrUpdate(HostFamily hostFamily);
    void deleteById(int id);
}
