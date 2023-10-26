package pro.sky.telegrambot.shelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.shelter.exceptions.AnimalNotFoundException;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.repository.AnimalRepository;
import pro.sky.telegrambot.shelter.repository.PhotoRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
public class AnimalService {
    private static final Logger logger = LoggerFactory.getLogger(AnimalService.class);
    private final AnimalRepository animalRepository;
    private final PhotoRepository photoRepository;

    public AnimalService(AnimalRepository animalRepository, PhotoRepository photoRepository) {
        this.animalRepository = animalRepository;
        this.photoRepository = photoRepository;
    }
    public Animal create(Animal animal){
        logger.info("Was invoked method for create animal");
        return animalRepository.save(animal);
    }
    public Animal getById(Long id){
        logger.info("Was invoked method for getById animal");
        return animalRepository.findById(id).orElseThrow(AnimalNotFoundException::new);
    }
    @Transactional
    public void delete(Long id){
        logger.info("Was invoked method for delete animal");
        photoRepository.deleteByAnimalId(id);
        Animal animal = animalRepository.findById(id).orElseThrow(AnimalNotFoundException::new);
        animalRepository.delete(animal);
    }
    public Collection<Animal> getAll(){
        logger.info("Was invoked method for get all animals");
        return animalRepository.findAll();
    }
}
