package pro.sky.telegrambot.shelter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.shelter.model.Photo;
import pro.sky.telegrambot.shelter.service.PhotoService;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @GetMapping("/from-disk/{id}")
    public void fromDisk(@PathVariable Long id, HttpServletResponse response){
        Photo photo = photoService.getById(id);
        response.setContentType(photo.getMediaType());
        response.setContentLength((int) photo.getFileSize());
        try (FileInputStream fis = new FileInputStream(photo.getFilePath())){
            fis.transferTo(response.getOutputStream());
        } catch (IOException e){
            logger.error("Failed to download Photo with id = " + id, e);
        }
    }
    @GetMapping("/from-db/{id}")
    public ResponseEntity<byte[]> fromDb(@PathVariable Long id){
        Photo photo = photoService.getById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
        headers.setContentLength(photo.getFileSize());
        return ResponseEntity.status(200).headers(headers).body(photo.getData());
    }
}
