package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Photo;
/**
 * Class to manage database transactions with table photo
 * @version $Revision: 1 $
 */
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
