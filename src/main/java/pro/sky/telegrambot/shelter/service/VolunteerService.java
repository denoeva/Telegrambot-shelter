package pro.sky.telegrambot.shelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.shelter.exceptions.VolunteerNotFoundException;
import pro.sky.telegrambot.shelter.model.Volunteer;
import pro.sky.telegrambot.shelter.repository.VolunteerRepository;

import java.util.Collection;

@Service
public class VolunteerService {
    private static final Logger logger = LoggerFactory.getLogger(VolunteerService.class);
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }
    public Volunteer create(Volunteer volunteer){
        logger.info("Was invoked method for create volunteer");
        return volunteerRepository.save(volunteer);
    }
    public Volunteer getById(Long id){
        logger.info("Was invoked method for getById volunteer");
        return volunteerRepository.findById(id).orElseThrow(VolunteerNotFoundException::new);
    }
    public Volunteer update(Long id, Volunteer volunteer){
        logger.info("Was invoked method for update volunteer");
        Volunteer existingVolunteer = volunteerRepository.findById(id)
                .orElseThrow(VolunteerNotFoundException::new);
        existingVolunteer.setChatId(volunteer.getChatId());
        existingVolunteer.setName(volunteer.getName());
        return volunteerRepository.save(existingVolunteer);
    }
    public void delete(Long id){
        logger.info("Was invoked method for delete volunteer");
        Volunteer volunteer = volunteerRepository.findById(id).orElseThrow(VolunteerNotFoundException::new);
        volunteerRepository.delete(volunteer);
    }
    public Collection<Volunteer> getAll(){
        logger.info("Was invoked method for get all volunteers");
        return volunteerRepository.findAll();
    }
}
