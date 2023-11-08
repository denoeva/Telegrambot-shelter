package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Report;
/**
 * Class to manage database transactions with table report
 * @version $Revision: 1 $
 */
public interface ReportRepository extends JpaRepository<Report, Long> {

}
