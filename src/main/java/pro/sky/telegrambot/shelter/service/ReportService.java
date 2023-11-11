package pro.sky.telegrambot.shelter.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.shelter.model.Report;
import pro.sky.telegrambot.shelter.repository.ReportRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Report service class
 *
 **/
@Service
public class ReportService {
    private final ReportRepository repository;
    public ReportService(ReportRepository reportRepository) {
        this.repository = reportRepository;
    }

    public void uploadReport(Long reportId,
                             Long chatId,
                             String reportText,
                             LocalDateTime dateTime,
                             Boolean checkedByVolunteer) {
        Report report = new Report();
        report.setReportId(reportId);
        report.setChatId(chatId);
        report.setReport(reportText);
        report.setDateTime(dateTime);
        report.setCheckedByVolunteer(checkedByVolunteer);
        this.repository.save(report);
    }

    public Optional findByReportId(Long reportId) {
        return this.repository.findById(reportId);
    }

    public Report save(Report report) {
        return this.repository.save(report);
    }

    public Report findByChatId(Long chatId) {
        return this.repository.findByChatId(chatId);
    }

    public Collection<Report> findListByChatId(Long chatId) {
        return this.repository.findListByChatId(chatId);
    }

    public void remove(Long reportId) {
        this.repository.deleteById(reportId);
    }

    public List<Report> getAll() {
        return this.repository.findAll();
    }


}
