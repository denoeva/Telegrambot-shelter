package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.shelter.model.Report;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Class to manage database transactions with table report
 * @version $Revision: 1 $
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(
            value = "SELECT r.chat_id FROM report r WHERE r.date_time < now() - interval '1 day'",
            nativeQuery = true)
    List<Long> findAllChatIdsWithLastReportOneDayAgo();
    Set<Report> findListByChatId(Long id);
    Report findByChatId(Long chatId);
}
