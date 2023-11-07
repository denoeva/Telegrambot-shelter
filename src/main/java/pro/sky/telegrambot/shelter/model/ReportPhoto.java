package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class ReportPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reportPhotoFilePath;
    private long reportPhotoFileSize;
    private String reportPhotoMediaType;
    private byte[] reportPhotoData;
    @OneToOne
    private Animal animal;

    public ReportPhoto(Long id, String reportPhotoFilePath, long reportPhotoFileSize, String reportPhotoMediaType, byte[] reportPhotoData, Animal animal) {
        this.id = id;
        this.reportPhotoFilePath = reportPhotoFilePath;
        this.reportPhotoFileSize = reportPhotoFileSize;
        this.reportPhotoMediaType = reportPhotoMediaType;
        this.reportPhotoData = reportPhotoData;
        this.animal = animal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportPhotoFilePath() {
        return reportPhotoFilePath;
    }

    public void setReportPhotoFilePath(String reportPhotoFilePath) {
        this.reportPhotoFilePath = reportPhotoFilePath;
    }

    public long getReportPhotoFileSize() {
        return reportPhotoFileSize;
    }

    public void setReportPhotoFileSize(long reportPhotoFileSize) {
        this.reportPhotoFileSize = reportPhotoFileSize;
    }

    public String getReportPhotoMediaType() {
        return reportPhotoMediaType;
    }

    public void setReportPhotoMediaType(String reportPhotoMediaType) {
        this.reportPhotoMediaType = reportPhotoMediaType;
    }

    public byte[] getReportPhotoData() {
        return reportPhotoData;
    }

    public void setReportPhotoData(byte[] reportPhotoData) {
        this.reportPhotoData = reportPhotoData;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportPhoto that = (ReportPhoto) o;
        return reportPhotoFileSize == that.reportPhotoFileSize && Objects.equals(id, that.id) && Objects.equals(reportPhotoFilePath, that.reportPhotoFilePath) && Objects.equals(reportPhotoMediaType, that.reportPhotoMediaType) && Arrays.equals(reportPhotoData, that.reportPhotoData) && Objects.equals(animal, that.animal);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, reportPhotoFilePath, reportPhotoFileSize, reportPhotoMediaType, animal);
        result = 31 * result + Arrays.hashCode(reportPhotoData);
        return result;
    }
}
