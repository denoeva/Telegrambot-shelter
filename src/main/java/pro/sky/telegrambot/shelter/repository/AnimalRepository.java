package pro.sky.telegrambot.shelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.shelter.model.Animal;
/**
 * Class to manage database transactions with table animals
 * @version $Revision: 1 $
 */
public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
