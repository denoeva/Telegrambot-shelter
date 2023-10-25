package pro.sky.telegrambot.shelter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.model.Photo;
import pro.sky.telegrambot.shelter.repository.AnimalRepository;
import pro.sky.telegrambot.shelter.repository.PhotoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
public class PhotoService {
    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);
    private final PhotoRepository photoRepository;
    private final AnimalRepository animalRepository;
    @Value("${path.to.photo.folder}")
    private Path photoPath;

    public PhotoService(PhotoRepository photoRepository, AnimalRepository animalRepository) {
        this.photoRepository = photoRepository;
        this.animalRepository = animalRepository;
    }
    public Photo getById(Long id) {
        logger.info("Was invoked method for getById");
        return photoRepository.findById(id).orElseThrow();
    }
    public Long save(Long animalId, MultipartFile multipartFile) throws IOException{
        logger.info("Was invoked method for save");

        // Step 1. Save at disk
        Files.createDirectories(photoPath);
        int dotIndex = multipartFile.getOriginalFilename().lastIndexOf(".");
        String fileExtension = multipartFile.getOriginalFilename().substring(dotIndex + 1);
        Path path = photoPath.resolve(animalId + "." + fileExtension);
        byte[] data = multipartFile.getBytes();
        Files.write(path, data, StandardOpenOption.CREATE);

        // Step 2. Save Database
        Animal animalReference = animalRepository.getById(animalId);
        Photo photo = photoRepository.findFirstByAnimal(animalReference).orElse(new Photo());
        photo.setAnimal(animalReference);
        photo.setMediaType(multipartFile.getContentType());
        photo.setFileSize(multipartFile.getSize());
        photo.setData(data);
        photo.setFilePath(path.toAbsolutePath().toString());
        photoRepository.save(photo);
        return photo.getId();
    }
}
