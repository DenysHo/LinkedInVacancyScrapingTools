package com.go.denys.selenium.automatization.linkedin.jobs.scaner.controller;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.scanner.LinkedInScannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.TimeRangeSelector.DAY;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.TimeRangeSelector.WEEK;

@Controller
@RequestMapping("/linkedin")
public class LinkedInScannerController {


    private static final Logger logger = LoggerFactory.getLogger(LinkedInScannerController.class);
    private final LinkedInScannerService scannerService;

    @Autowired
    public LinkedInScannerController(LinkedInScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping("/")
    @ResponseBody
    public String initialize(@RequestParam(value = "message", required = false) String message) {
        return "<html>" +
                "<body>" +
                "<h1>Welcome to my LinkedIn Vacancy Scraping Tools!</h1>" +
                (message != null ? "<p>" + message + "</p>" : "") +
                "<form action='/scan' method='post'>" +
                "<button type='submit'>Click me to check new ad!</button>" +
                "</form>" +
                "<form action='/test' method='post'>" +
                "<button type='submit'>Click me to Test!</button>" +
                "</form>" +
                "</body>" +
                "</html>";
    }

    @PostMapping("/scan")
    public String scan() {
        ScannerFilter scannerFilter = ScannerFilter.builder()
                .timeRange(WEEK)
                .keywords("Java Developer")
                .location("Germany")
                .limit(0)
                .previous(true)
                .build();

        String message;
        try {
            scannerService.scan(scannerFilter);
            message = "Action processed successfully!";
        } catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
            message = "Oops, something went wrong:(";
        }

        return "redirect:/?message=" + message;
    }

    @PostMapping("/test")
    public String test() {
        ScannerFilter scannerFilter = ScannerFilter.builder()
                .timeRange(DAY)
                .keywords("Java Developer")
                .location("Germany")
                .limit(5)
                .previous(false)
                .build();

        String message;
        try {
            scannerService.scan(scannerFilter);
            message = "Test processed successfully!";
        } catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
            message = "Oops, something went wrong:(";
        }

        return "redirect:/?message=" + message;
    }

}
