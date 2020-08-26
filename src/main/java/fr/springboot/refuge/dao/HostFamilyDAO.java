package fr.springboot.refuge.dao;

import fr.springboot.refuge.entity.HostFamily;
import java.util.List;

public interface HostFamilyDAO {
    List<HostFamily> findAll();
    HostFamily findById(int id);
    void saveOrUpdate(HostFamily hostFamily);
    void deleteById(int id);
}
