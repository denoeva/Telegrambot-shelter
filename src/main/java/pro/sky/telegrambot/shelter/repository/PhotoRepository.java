package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
