package com.go.denys.selenium.automatization;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaJobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.db.JobAdHistoryService;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.filter.EuropaJobAdFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EuropaJobAdFilterTest {

    private List<EuropaJobAd> jobs;
    @InjectMocks
    private EuropaJobAdFilter jobAdFilter;
    @Mock
    private JobAdHistoryService jobAdHistoryService;
    private List<EuropaJobAd> expected;
    private List<EuropaJobAd> actual;
    private EuropaScannerFilter filter;


    @BeforeEach
    public void initialize() {
        jobs = new ArrayList<>();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        filter = EuropaScannerFilter.builder().uniqueness(false).previous(false).build();
        jobAdFilter = new EuropaJobAdFilter(jobAdHistoryService);
    }

    @Test
    public void filterTest() {
        jobs.add(EuropaJobAd.builder().languages("Dutch").build());
        jobs.add(EuropaJobAd.builder().languages("English").build());
        jobs.add(EuropaJobAd.builder().languages(null).build());
        jobs.add(EuropaJobAd.builder().languages("").build());

        expected.add(EuropaJobAd.builder().languages("English").build());
        expected.add(EuropaJobAd.builder().languages(null).build());
        expected.add(EuropaJobAd.builder().languages("").build());

        filter.getLanguages().add("English");

        actual = jobAdFilter.filter(jobs, filter);
        Assertions.assertEquals(expected, actual);
    }

}
