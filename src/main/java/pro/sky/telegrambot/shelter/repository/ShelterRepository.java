package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {

}
