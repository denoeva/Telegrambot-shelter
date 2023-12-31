package pro.sky.telegrambot.shelter.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;
/**
 * Class to store photos of animals sent by users in the reports
 * @version $Revision: 1 $
 */
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    @OneToOne
    private Animal animal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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
        Photo photo = (Photo) o;
        return fileSize == photo.fileSize && Objects.equals(id, photo.id) && Objects.equals(filePath, photo.filePath) && Objects.equals(mediaType, photo.mediaType) && Arrays.equals(data, photo.data) && Objects.equals(animal, photo.animal);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, animal);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
