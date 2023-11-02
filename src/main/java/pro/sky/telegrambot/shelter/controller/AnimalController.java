package pro.sky.telegrambot.shelter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.model.Volunteer;
import pro.sky.telegrambot.shelter.service.AnimalService;
import pro.sky.telegrambot.shelter.service.PhotoService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    private static final Logger logger = LoggerFactory.getLogger(AnimalController.class);
    private final AnimalService animalService;
    private final PhotoService photoService;

    public AnimalController(AnimalService animalService, PhotoService photoService) {
        this.animalService = animalService;
        this.photoService = photoService;
    }
    @PostMapping
    public Animal create(@RequestBody Animal animal){return animalService.create(animal);
    }
    @PostMapping(value = "/{animalId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> save(@PathVariable Long animalId, @RequestBody MultipartFile multipartFile){
        try {
            return ResponseEntity.ok(photoService.save(animalId, multipartFile));
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getById(@PathVariable("id") Long id) {
        Animal animal = animalService.getById(id);
        if (animal == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Animal> update(@PathVariable("id") Long id, @RequestBody Animal animal) {
        Animal foundAnimal = animalService.update(id, animal);
        if (foundAnimal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundAnimal);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id")Long id){
        animalService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity <Collection<Animal>> getAll() {
        return ResponseEntity.ok(animalService.getAll());
    }
}
