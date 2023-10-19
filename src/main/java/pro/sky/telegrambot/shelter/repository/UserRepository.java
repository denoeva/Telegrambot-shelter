package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

}
