package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
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
    @OneToOne
    private Animal animal;
    @OneToOne(mappedBy = "report", cascade = CascadeType.PERSIST)
    private ReportPhoto reportPhoto;

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public ReportPhoto getReportPhoto() {
        return reportPhoto;
    }

    public void setReportPhoto(ReportPhoto reportPhoto) {
        this.reportPhoto = reportPhoto;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
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
        Report report = (Report) o;
        return checkedByVolunteer == report.checkedByVolunteer && Objects.equals(reportId, report.reportId) && Objects.equals(chatId, report.chatId) && Objects.equals(reportText, report.reportText) && Objects.equals(dateTime, report.dateTime) && Objects.equals(animal, report.animal) && Objects.equals(reportPhoto, report.reportPhoto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportId, chatId, reportText, dateTime, checkedByVolunteer, animal, reportPhoto);
    }
}
