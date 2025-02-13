package com.go.denys.selenium.automation.linkedin.jobs.scanner.controller;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScanResult;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.requests.LikedInRequest;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.responses.LinkedInResponse;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.scanner.LinkedInScannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/linkedin")
public class LinkedInScannerRestController {

    private final LinkedInScannerService scannerService;

    @Autowired
    public LinkedInScannerRestController(LinkedInScannerService scannerService) {
        this.scannerService = scannerService;
    }


    @PostMapping("/scan")
    public ResponseEntity<LinkedInResponse> scan(@RequestBody LikedInRequest request) {
        ScanResult<JobAd> result;

        //todo ex handler
        try {
            result = scannerService.scan(request.getFilter());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LinkedInResponse response = LinkedInResponse.builder()
                .ads(result.getAds())
                .adsAfterFilter(result.getAdsAfterFilter())
                .adsFound(result.getAdsFound()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
