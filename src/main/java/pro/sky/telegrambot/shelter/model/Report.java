package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Long chatId;
    private String report;
    private LocalDateTime dateTime;
    private boolean checkedByVolunteer;

    public Report(Long reportId, Long chatId, String report, LocalDateTime dateTime, boolean checkedByVolunteer) {
        this.reportId = reportId;
        this.chatId = chatId;
        this.report = report;
        this.dateTime = dateTime;
        this.checkedByVolunteer = checkedByVolunteer;
    }

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCheckedByVolunteer() {
        return checkedByVolunteer;
    }

    public void setCheckedByVolunteer(boolean checkedByVolunteer) {
        this.checkedByVolunteer = checkedByVolunteer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report1 = (Report) o;
        return checkedByVolunteer == report1.checkedByVolunteer && Objects.equals(reportId, report1.reportId) && Objects.equals(chatId, report1.chatId) && Objects.equals(report, report1.report) && Objects.equals(dateTime, report1.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, chatId, report, dateTime, checkedByVolunteer);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", chatId=" + chatId +
                ", report='" + report + '\'' +
                ", dateTime=" + dateTime +
                ", checkedByVolunteer=" + checkedByVolunteer +
                '}';
    }
}
