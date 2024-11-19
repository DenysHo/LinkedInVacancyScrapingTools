package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.filter;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaJobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.EuropaScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.db.JobAdHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.print;

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
        print(new ArrayList<>(jobs));

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
