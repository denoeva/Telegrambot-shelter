package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.shelter.model.Report;

import java.util.Set;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
    Set<Report> findListByChatId(Long id);
    Report findByChatId(Long chatId);
}
