package pro.sky.telegrambot.shelter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.shelter.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.repository.AnimalRepository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Class of service tests for Animals
 *
 **/
@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {
    private final Long testId = 1L;

    Animal animal1 = new Animal(
            testId,
            Animal.TypeOfAnimal.CAT,
            "Cat1",
            "Long",
            "Male",
            "Red",
            LocalDate.of(2014, 07, 10),
            "Health",
            "Cute",
            false);
    Animal animal2 = new Animal(
            2L,
            Animal.TypeOfAnimal.CAT,
            "Cat2",
            "Short",
            "Male",
            "Black",
            LocalDate.of(2005, 12, 01),
            "Health",
            "Cute",
            false);
    Animal animal3 = new Animal(
            3L,
            Animal.TypeOfAnimal.DOG,
            "Dog1",
            "Short",
            "Male",
            "Orange",
            LocalDate.of(2012, 10, 22),
            "Health",
            "Cute",
            false);

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AnimalService animalService;

    @Test
    public void getAnimalByIdTest() {
        Mockito.when(animalRepository.findById(testId)).thenReturn(Optional.of(animal1));
        Animal result = animalService.getById(testId);
        Assertions.assertEquals(animal1, result);
    }

    @Test
    public void getAnimalByIdTestNotFound() throws AnimalNotFoundException {
        Mockito.when(animalRepository.findById(testId)).thenReturn(Optional.empty());
        Assertions.assertThrows(AnimalNotFoundException.class, () -> animalService.getById(testId));
    }

    @Test
    public void createAnimalTest() {
        Mockito.when(animalRepository.save(animal1)).thenReturn(animal1);
        Animal createAnimal = animalService.create(animal1);
        Mockito.verify(animalRepository, Mockito.times(1)).save(animal1);
        Assertions.assertEquals(animal1.getId(), createAnimal.getId());
        Assertions.assertEquals(animal1.getName(), createAnimal.getName());
        Assertions.assertEquals(animal1.getBreed(), createAnimal.getBreed());
        Assertions.assertEquals(animal1.getDOB(), createAnimal.getDOB());
    }

    @Test
    public void updateAnimalTest() {

        Animal updatedAnimal1 = animalService.update(testId, animal1);
        Animal updatedAnimal2 = animalService.update(2L, animal2);
        Animal updatedAnimal3 = animalService.update(3L, animal3);

        Assertions.assertEquals(updatedAnimal1.getId(), testId);
        Assertions.assertEquals(updatedAnimal1.getTypeOfAnimal(), Animal.TypeOfAnimal.CAT);
        Assertions.assertEquals(updatedAnimal1.getName(), "Cat1");
        Assertions.assertEquals(updatedAnimal1.getBreed(), "Long");
        Assertions.assertEquals(updatedAnimal1.getGender(), "Male");
        Assertions.assertEquals(updatedAnimal1.getColor(), "Red");
        Assertions.assertEquals(updatedAnimal1.getDOB(), LocalDate.of(2014, 07, 10));
        Assertions.assertEquals(updatedAnimal1.getHealth(), "Health");
        Assertions.assertEquals(updatedAnimal1.getCharacteristic(), "Cute");
        Assertions.assertEquals(updatedAnimal1.getAttached(), false);

        Assertions.assertEquals(updatedAnimal1.getId(), Long.valueOf(2L));
        Assertions.assertEquals(updatedAnimal1.getTypeOfAnimal(), Animal.TypeOfAnimal.CAT);
        Assertions.assertEquals(updatedAnimal1.getName(), "Cat2");
        Assertions.assertEquals(updatedAnimal1.getBreed(), "Short");
        Assertions.assertEquals(updatedAnimal1.getGender(), "Male");
        Assertions.assertEquals(updatedAnimal1.getColor(), "Black");
        Assertions.assertEquals(updatedAnimal1.getDOB(), LocalDate.of(2005, 12, 01));
        Assertions.assertEquals(updatedAnimal1.getHealth(), "Health");
        Assertions.assertEquals(updatedAnimal1.getCharacteristic(), "Cute");
        Assertions.assertEquals(updatedAnimal1.getAttached(), false);

        Assertions.assertEquals(updatedAnimal1.getId(), Long.valueOf(3L));
        Assertions.assertEquals(updatedAnimal1.getTypeOfAnimal(), Animal.TypeOfAnimal.DOG);
        Assertions.assertEquals(updatedAnimal1.getName(), "Dog1");
        Assertions.assertEquals(updatedAnimal1.getBreed(), "Short");
        Assertions.assertEquals(updatedAnimal1.getGender(), "Male");
        Assertions.assertEquals(updatedAnimal1.getColor(), "Orange");
        Assertions.assertEquals(updatedAnimal1.getDOB(), LocalDate.of(2012, 10, 22));
        Assertions.assertEquals(updatedAnimal1.getHealth(), "Health");
        Assertions.assertEquals(updatedAnimal1.getCharacteristic(), "Cute");
        Assertions.assertEquals(updatedAnimal1.getAttached(), false);

        Animal updatedAnimal4 = new Animal();
        Assertions.assertThrows(AnimalNotFoundException.class, () -> animalService.update(4L, updatedAnimal4));
    }
}
