package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Animal;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
