package fr.springboot.refuge.entity;


import org.hibernate.annotations.ColumnDefault;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "host_family")
public class HostFamily extends Person{

    @OneToMany(mappedBy = "hostFamily",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Animal> animals;

    @ColumnDefault("True")
    private boolean free;

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
