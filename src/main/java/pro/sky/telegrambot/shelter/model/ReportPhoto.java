package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class ReportPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private byte[] reportPhotoData;
    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
    public Report getReport() {
        return report;
    }
    public void setReport(Report report) {
        this.report = report;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getReportPhotoData() {
        return reportPhotoData;
    }

    public void setReportPhotoData(byte[] reportPhotoData) {
        this.reportPhotoData = reportPhotoData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportPhoto that = (ReportPhoto) o;
        return Objects.equals(id, that.id) && Arrays.equals(reportPhotoData, that.reportPhotoData) && Objects.equals(report, that.report);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, report);
        result = 31 * result + Arrays.hashCode(reportPhotoData);
        return result;
    }
}
