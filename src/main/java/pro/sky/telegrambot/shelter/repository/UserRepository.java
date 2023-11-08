package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Users;

import java.util.List;
import java.util.Optional;

/**
 * Class to manage database transactions with table users
 * @version $Revision: 1 $
 */
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUserByChatId(Long chatId);
    List<Users> findAllByName(String name);
    Optional<Users> findUsersByName(String name);
}
