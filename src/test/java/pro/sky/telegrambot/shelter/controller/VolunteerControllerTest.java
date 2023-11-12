package pro.sky.telegrambot.shelter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.shelter.model.Volunteer;
import pro.sky.telegrambot.shelter.service.VolunteerService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Controller test class for Volunteers
 *
 **/
@WebMvcTest(VolunteerController.class)
public class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolunteerService volunteerService;

    @Test
    void createVolunteerTest() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("newVolunteer");
        volunteer.setChatId(1L);
        when(volunteerService.create(volunteer)).thenReturn(volunteer);
        Volunteer result = volunteerService.create(volunteer);

        assertEquals(volunteer, result);
    }

    @Test
    void getVolunteerByIdTest() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        when(volunteerService.getById(anyLong())).thenReturn(volunteer);
        mockMvc.perform(
                        get("/volunteer/{id}", 1L))
                .andExpect(status().isOk());
        verify(volunteerService).getById(1L);
    }

    @Test
    void updateVolunteerByIdTest() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setName("Volunteer");
        when(volunteerService.update(volunteer.getId(), volunteer)).thenReturn(volunteer);
        Volunteer result = volunteerService.update(1L, volunteer);

        assertEquals(volunteer, result);
    }

    @Test
    void removeVolunteerByIdTest() throws Exception {
        mockMvc.perform(
                        delete("/volunteer/{id}", 1L))
                .andExpect(status().isOk());
        verify(volunteerService).delete(1L);
    }

    @Test
    void getAllVolunteersTest() throws Exception {
        mockMvc.perform(
                        get("/volunteer"))
                .andExpect(status().isOk());
        verify(volunteerService).getAll();
    }
}
