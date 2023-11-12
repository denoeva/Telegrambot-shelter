package pro.sky.telegrambot.shelter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.shelter.model.Report;
import pro.sky.telegrambot.shelter.service.ReportService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Controller test class for Reports
 *
 **/
@WebMvcTest(ReportController.class)
public class ReportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @Test
    void getReportByIdTest() throws Exception {
        Report newReport = new Report();
        newReport.setReportId(1L);
        Optional<Report> optionalReport = Optional.of(newReport);
        when(reportService.findByReportId(anyLong())).thenReturn(optionalReport);
        mockMvc.perform(
                        get("/reports/{reportId}", 1L))
                .andExpect(status().isOk());
        verify(reportService).findByReportId(1L);
    }

    @Test
    void deleteReportByIdTest() throws Exception {
        mockMvc.perform(
                        delete("/reports/{reportId}", 1L))
                .andExpect(status().isOk());
        verify(reportService).remove(1L);
    }

    @Test
    void getAllReportsTest() throws Exception {
        mockMvc.perform(
                        get("/reports/getAll"))
                .andExpect(status().isOk());
        verify(reportService).getAll();
    }
}
