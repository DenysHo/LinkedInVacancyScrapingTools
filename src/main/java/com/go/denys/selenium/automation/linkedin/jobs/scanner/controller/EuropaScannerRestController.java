package com.go.denys.selenium.automation.linkedin.jobs.scanner.controller;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaJobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScanResult;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.requests.EuropaRequest;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.responses.EuropaResponse;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.responses.Response;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.filter.JobAdFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.scanner.EuropeScannerService;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/europa")
public class EuropaScannerRestController {

    private static final Logger logger = LoggerFactory.getLogger(EuropaScannerRestController.class);
    private final EuropeScannerService scannerService;

    @Autowired
    public EuropaScannerRestController(EuropeScannerService scannerService) {
        this.scannerService = scannerService;
    }


    @PostMapping("/scan")
    public ResponseEntity<EuropaResponse> scan(@RequestBody EuropaRequest request) {
        ResponseEntity<EuropaResponse> responseEntity;
        try {
            ScanResult<EuropaJobAd> result = scannerService.scan(request.getFilter());
            EuropaResponse response = EuropaResponse.builder()
                    .ads(result.getAds())
                    .adsAfterFilter(result.getAdsAfterFilter())
                    .adsFound(result.getAdsFound()).build();
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
