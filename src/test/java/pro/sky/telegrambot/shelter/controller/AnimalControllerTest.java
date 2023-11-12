package pro.sky.telegrambot.shelter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.shelter.model.Animal;
import pro.sky.telegrambot.shelter.service.AnimalService;
import pro.sky.telegrambot.shelter.service.PhotoService;
import org.json.JSONObject;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller test class for Animals
 *
 **/
@WebMvcTest(AnimalController.class)
public class AnimalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnimalService animalService;
    @MockBean
    private PhotoService photoService;

    @Test
    void getAnimalByIdTest() throws Exception {
        Animal animal = new Animal();
        animal.setId(1L);
        when(animalService.getById(anyLong())).thenReturn(animal);
        mockMvc.perform(
                        get("/animal/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(animalService).getById(1L);
    }

    @Test
    void updateAnimalByIdTest() throws Exception {
        Animal animal = new Animal();
        animal.setId(1L);
        animal.setName("Animal");
        JSONObject userObject = new JSONObject();
        userObject.put("id", 1L);
        userObject.put("name", "Animal");
        when(animalService.update(animal.getId(), animal)).thenReturn(animal);
        mockMvc.perform(
                        put("/animal/{id}", 1L)
                                .content(userObject.toString())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(userObject.toString()));
        verify(animalService).update(animal.getId(), animal);
    }

    @Test
    void removeAnimalByIdTest() throws Exception {
        mockMvc.perform(
                        delete("/animal/{id}", 1L))
                .andExpect(status().isOk());
        verify(animalService).delete(1L);
    }
}
