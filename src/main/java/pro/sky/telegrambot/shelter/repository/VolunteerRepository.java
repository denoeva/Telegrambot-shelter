package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
}
