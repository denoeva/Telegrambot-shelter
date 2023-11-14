package pro.sky.telegrambot.shelter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    private Long chatId;
    private String reportText;
    private LocalDateTime dateTime;
    private boolean checkedByVolunteer;

    public Report(Long reportId, Long chatId, String reportText, LocalDateTime dateTime, boolean checkedByVolunteer) {
        this.reportId = reportId;
        this.chatId = chatId;
        this.reportText = reportText;
        this.dateTime = dateTime;
        this.checkedByVolunteer = checkedByVolunteer;
    }

    public Report() {
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
        return reportText;
    }

    public void setReport(String report) {
        this.reportText = report;
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
        return checkedByVolunteer == report1.checkedByVolunteer && Objects.equals(reportId, report1.reportId) && Objects.equals(chatId, report1.chatId) && Objects.equals(reportText, report1.reportText) && Objects.equals(dateTime, report1.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, chatId, reportText, dateTime, checkedByVolunteer);
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId=" + reportId +
                ", chatId=" + chatId +
                ", report='" + reportText + '\'' +
                ", dateTime=" + dateTime +
                ", checkedByVolunteer=" + checkedByVolunteer +
                '}';
    }
}
