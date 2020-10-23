package fr.springboot.refuge.entity;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="animal", uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String race;

    private String name;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    @Enumerated(EnumType.STRING)
    private Species species;


    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "is_adopted")
    @ColumnDefault("False")
    private boolean isAdopted;

    @OneToMany(mappedBy = "animal",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    List<VeterinaryCare> veterinaryCares;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_host_family")
    private HostFamily hostFamily;


    @OneToOne(mappedBy = "adoptedAnimal")
    private AdoptAnimal adoption;

    public Animal() {
    }

    public Animal(int id, Species species, String race, String name, LocalDate birthDate, Sexe sexe, LocalDate arrivalDate, boolean isAdopted) {
        this.id = id;
        this.species = species;
        this.race = race;
        this.name = name;
        this.birthDate = birthDate;
        this.sexe = sexe;
        this.arrivalDate = arrivalDate;
        this.isAdopted = isAdopted;
    }

    public int getId() {
        return id;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public boolean isAdopted() {
        return isAdopted;
    }

    public void setAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    public void setHostFamily(HostFamily hostFamily) {
        this.hostFamily = hostFamily;
    }

    public HostFamily getHostFamily() {
        return hostFamily;
    }

    public AdoptAnimal getAdoption() {
        return adoption;
    }

    public void setAdoption(AdoptAnimal adoption) {
        this.adoption = adoption;
    }
}
