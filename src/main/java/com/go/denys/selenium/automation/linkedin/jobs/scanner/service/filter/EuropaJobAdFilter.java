package com.go.denys.selenium.automation.linkedin.jobs.scanner.service.filter;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaJobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.service.db.JobAdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.go.denys.selenium.automation.linkedin.jobs.scanner.util.JobAdUtil.print;

@Service
public class EuropaJobAdFilter extends JobAdFilter<EuropaScannerFilter, EuropaJobAd> {

    @Autowired
    public EuropaJobAdFilter(JobAdHistoryService jobAdHistoryService) {
        super(jobAdHistoryService);
    }

    @Override
    public List<EuropaJobAd> filter(List<EuropaJobAd> jobs, EuropaScannerFilter filter) {
        adsCountForLogging = jobs.size();

        jobs = filterLocations(jobs, filter);
        logFilter("Locations", jobs.size());

        return super.filter(jobs, filter);
    }

    private List<EuropaJobAd> filterLocations(List<EuropaJobAd> jobs, EuropaScannerFilter filter) {
        if (!filter.getLanguages().isEmpty()) {
            jobs = jobs.stream()
                    .filter(job -> job.getLanguages() == null || job.getLanguages().isEmpty() || filter.getLanguages().stream().anyMatch(job.getLanguages()::contains))
                    .toList();
        }

        return jobs;
    }


}
