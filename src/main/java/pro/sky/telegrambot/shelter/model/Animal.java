package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
/**
 * Class to represent model of animals
 * @version $Revision: 1 $
 */
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeOfAnimal typeOfAnimal;
    public enum TypeOfAnimal { CAT, DOG}
    private String name;
    private String breed;
    private String gender;
    private String color;
    private LocalDate DOB;
    private String health;
    private String characteristic;
    private Boolean attached;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    public Animal(){
    }
    public Animal(Long id, TypeOfAnimal typeOfAnimal, String name, String breed, String gender, String color, LocalDate DOB, String health, String characteristic, Boolean attached) {
        this.id = id;
        this.typeOfAnimal = typeOfAnimal;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.color = color;
        this.DOB = DOB;
        this.health = health;
        this.characteristic = characteristic;
        this.attached = attached;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfAnimal getTypeOfAnimal() {
        return typeOfAnimal;
    }

    public void setTypeOfAnimal(TypeOfAnimal typeOfAnimal) {
        this.typeOfAnimal = typeOfAnimal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public Boolean getAttached() {
        return attached;
    }

    public void setAttached(Boolean attached) {
        this.attached = attached;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id) && Objects.equals(typeOfAnimal, animal.typeOfAnimal) && Objects.equals(name, animal.name) && Objects.equals(breed, animal.breed) && Objects.equals(gender, animal.gender) && Objects.equals(color, animal.color) && Objects.equals(DOB, animal.DOB) && Objects.equals(health, animal.health) && Objects.equals(characteristic, animal.characteristic) && Objects.equals(attached, animal.attached);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeOfAnimal, name, breed, gender, color, DOB, health, characteristic, attached);
    }
}
