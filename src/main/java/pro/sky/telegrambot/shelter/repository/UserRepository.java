package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.User;
/**
 * Class to manage database transactions with table users
 * @version $Revision: 1 $
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
