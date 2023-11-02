package pro.sky.telegrambot.shelter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.shelter.model.Volunteer;
import pro.sky.telegrambot.shelter.service.VolunteerService;

import java.util.Collection;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public Volunteer create(@RequestBody Volunteer volunteer){
        return volunteerService.create(volunteer);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getById(@PathVariable("id") Long id) {
        Volunteer volunteer = volunteerService.getById(id);
        if (volunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> update(@PathVariable("id") Long id, @RequestBody Volunteer volunteer){
        Volunteer foundVolunteer = volunteerService.update(id, volunteer);
        if (foundVolunteer == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundVolunteer);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        volunteerService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Volunteer>> getAll(){
        return ResponseEntity.ok(volunteerService.getAll());
    }
}
