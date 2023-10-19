package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeOfShelter;
    private List<Animal> animals;
    private String infoOfShelter;
    private Calendar timetableOfWork;
    private String address;
    private String locationMap;
    private String numberContactOfSecurity;
    private String safetyRecommendations;

    public Shelter() {
    }

    public Shelter(Long id, String typeOfShelter, List<Animal> animals, String infoOfShelter, Calendar timetableOfWork, String address, String locationMap, String numberContactOfSecurity, String safetyRecommendations) {
        this.id = id;
        this.typeOfShelter = typeOfShelter;
        this.animals = animals;
        this.infoOfShelter = infoOfShelter;
        this.timetableOfWork = timetableOfWork;
        this.address = address;
        this.locationMap = locationMap;
        this.numberContactOfSecurity = numberContactOfSecurity;
        this.safetyRecommendations = safetyRecommendations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeOfShelter() {
        return typeOfShelter;
    }

    public void setTypeOfShelter(String typeOfShelter) {
        this.typeOfShelter = typeOfShelter;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public String getInfoOfShelter() {
        return infoOfShelter;
    }

    public void setInfoOfShelter(String infoOfShelter) {
        this.infoOfShelter = infoOfShelter;
    }

    public Calendar getTimetableOfWork() {
        return timetableOfWork;
    }

    public void setTimetableOfWork(Calendar timetableOfWork) {
        this.timetableOfWork = timetableOfWork;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocationMap() {
        return locationMap;
    }

    public void setLocationMap(String locationMap) {
        this.locationMap = locationMap;
    }

    public String getNumberContactOfSecurity() {
        return numberContactOfSecurity;
    }

    public void setNumberContactOfSecurity(String numberContactOfSecurity) {
        this.numberContactOfSecurity = numberContactOfSecurity;
    }

    public String getSafetyRecommendations() {
        return safetyRecommendations;
    }

    public void setSafetyRecommendations(String safetyRecommendations) {
        this.safetyRecommendations = safetyRecommendations;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id) && Objects.equals(typeOfShelter, shelter.typeOfShelter) && Objects.equals(animals, shelter.animals) && Objects.equals(infoOfShelter, shelter.infoOfShelter) && Objects.equals(timetableOfWork, shelter.timetableOfWork) && Objects.equals(address, shelter.address) && Objects.equals(locationMap, shelter.locationMap) && Objects.equals(numberContactOfSecurity, shelter.numberContactOfSecurity) && Objects.equals(safetyRecommendations, shelter.safetyRecommendations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeOfShelter, animals, infoOfShelter, timetableOfWork, address, locationMap, numberContactOfSecurity, safetyRecommendations);
    }
}
