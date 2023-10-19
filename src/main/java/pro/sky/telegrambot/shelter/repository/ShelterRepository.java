package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Shelter;
/**
 * Class to manage database transactions with table shelter
 * @version $Revision: 1 $
 */
public interface ShelterRepository extends JpaRepository<Shelter, Long> {

}
