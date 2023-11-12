package pro.sky.telegrambot.shelter.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.shelter.model.Report;
import pro.sky.telegrambot.shelter.repository.ReportRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Class of service tests for Reports
 *
 **/
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getReportByIdTest() {
        long testId = 1;
        Report newReport = new Report();
        newReport.setReportId(testId);
        when(reportRepository.findById(testId)).thenReturn(Optional.of(newReport));
        Optional<Report> result = reportService.findByReportId(testId);

        assertEquals(Optional.of(newReport), result);
    }

    @Test
    void deleteReportByIdTest() {
        long testId = 1;
        Report newReport = new Report();
        newReport.setReportId(testId);
        ReportRepository mockRepository = mock(ReportRepository.class);
        doNothing().when(mockRepository).deleteById(testId);
        ReportService service = new ReportService(mockRepository);
        service.remove(testId);
        Mockito.verify(mockRepository, Mockito.times(1)).deleteById(testId);
    }

    @Test
    void getAllReportsTest() {
        List<Report> reportDataList = new ArrayList<>();
        Report newReport1 = new Report();
        newReport1.setReportId(1L);
        Report newReport2 = new Report();
        newReport2.setReportId(2L);
        Mockito.when(reportRepository.findAll()).thenReturn(reportDataList);
        ReportService service = new ReportService(reportRepository);
        Collection<Report> result = service.getAll();
        Assertions.assertEquals(reportDataList, result);
        Mockito.verify(reportRepository, Mockito.times(1)).findAll();
    }


}
