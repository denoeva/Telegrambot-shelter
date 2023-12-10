package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.model.Users;

import java.util.List;

/**
 * Class to manage database transactions with table users
 * @version $Revision: 1 $
 */
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUserByChatId(Long chatId);
    @Query(
            value = "SELECT * FROM USERS u JOIN report r ON u.chat_id = r.chat_id WHERE r.date_time < now() - interval '2 days'",
            nativeQuery = true)
    List<Users> findAllUsersWithLastReportTwoDaysAgo();
    @Query("select u.animal from Users u where u.chatId = :chatId")
    Animal findAnimal(@Param("chatId")Long chatId);
}
