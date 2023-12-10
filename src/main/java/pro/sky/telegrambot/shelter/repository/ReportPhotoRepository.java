package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.ReportPhoto;

public interface ReportPhotoRepository extends JpaRepository<ReportPhoto, Long> {
}
