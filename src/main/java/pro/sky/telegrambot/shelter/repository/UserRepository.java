package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
