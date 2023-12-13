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

    @Query(
            value = "select distinct * from users u join report r ON u.chat_id = r.chat_id WHERE u.attached = true AND r.date_time < now() - interval '30 days' AND (select count(*) from report r join users u on r.chat_id = u.chat_id where r.checked_by_volunteer = true) > 29",
            nativeQuery = true)
    List<Users> findAllUserChatIDsWhichPassedTestPeriod();

    @Query(
            value = "select distinct * from users u join report r ON u.chat_id = r.chat_id WHERE u.attached = true AND r.date_time < now() - interval '30 days' AND (select count(*) from report r join users u on r.chat_id = u.chat_id where r.checked_by_volunteer = true) < 30",
            nativeQuery = true)
    List<Users> findAllUserChatIDsWhichFailedTestPeriod();

    @Query("select u.animal from Users u where u.chatId = :chatId")
    Animal findAnimal(@Param("chatId")Long chatId);
}
