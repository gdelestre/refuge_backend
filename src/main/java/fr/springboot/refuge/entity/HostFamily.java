package fr.springboot.refuge.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "host_family", uniqueConstraints={@UniqueConstraint(columnNames={"phone_number"})})
public class HostFamily extends Person{

    @OneToMany(mappedBy = "hostFamily",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Animal> animals;


    private boolean free = true;

    public HostFamily(String firstName, String lastName, int zipCode, String city, String streetName, int streetNumber, String phoneNumber) {
        super(firstName, lastName, zipCode, city, streetName, streetNumber, phoneNumber);
    }

    public HostFamily() {
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
