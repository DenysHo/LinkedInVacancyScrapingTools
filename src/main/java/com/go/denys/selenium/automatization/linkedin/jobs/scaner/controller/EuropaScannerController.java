package com.go.denys.selenium.automatization.linkedin.jobs.scaner.controller;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.Location;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.scanner.EuropeScannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.Location.*;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.Profile.*;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.Profile.EN;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.Profile.RU;

@Controller
@RequestMapping("/europa")
public class EuropaScannerController {

    private static final Logger logger = LoggerFactory.getLogger(EuropaScannerController.class);
    private final EuropeScannerService scannerService;

    @Autowired
    public EuropaScannerController(EuropeScannerService scannerService) {
        this.scannerService = scannerService;
    }

    @GetMapping("/")
    @ResponseBody
    public String initialize(@RequestParam(value = "message", required = false) String message) {
        return "<html>" +
                "<body>" +
                "<h1>Welcome to my EURopean Employment Services Vacancy Scraping Tools!</h1>" +
                (message != null ? "<p>" + message + "</p>" : "") +
                "<form action='/europa/scan' method='post'>" +
                "<button type='submit'>Click me to check new ad!</button>" +
                "</form>" +
                "</body>" +
                "</html>";
    }

    @PostMapping("/scan")
    public String scan() {
        EuropaScannerFilter scannerFilter = EuropaScannerFilter.builder()
                .keywords("Java Developer")
                .profiles(List.of(EN, UK, RU))
                .locations(List.of(Location.SWEDEN))
                //.locations(List.of(FR))
                //.locations(List.of(ALL))
                //.locations(List.of(CZ))
                //.locations(List.of(BELGIUM))
                //.locations(List.of(AUSTRIA))
                //.locations(List.of(LUXEMBOURG))
                .limit(0)
                .previous(true)
                .languages(List.of(EN.getLanguage(), RU.getLanguage(), UK.getLanguage()))
                .build();

        String message;
        try {
            scannerService.scan(scannerFilter);
            message = "Action processed successfully!";
        } catch (Exception e) {
            logger.info("An error occurred: {}", e.getMessage(), e);
            message = "Oops, something went wrong:(";
        }

        return "redirect:/europa/?message=" + message;
    }

}
