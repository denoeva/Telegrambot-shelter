package pro.sky.telegrambot.shelter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.shelter.model.Report;
import pro.sky.telegrambot.shelter.service.ReportService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{reportId}")
    public Optional findReport(@PathVariable("reportId") Long reportId) {
        return this.reportService.findByReportId(reportId);
    }

    @DeleteMapping("/{reportId}")
    public void remove(@PathVariable("reportId") Long reportId) {
        this.reportService.remove(reportId);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Report>> getAll() {
        return ResponseEntity.ok(this.reportService.getAll());
    }
}
