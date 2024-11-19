package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.db;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.entity.JobAdHistoryEntity;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.repo.JobAdHistoryRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobAdHistoryService {

    private final JobAdHistoryRepo jobAdHistoryRepo;

    @Autowired
    public JobAdHistoryService(JobAdHistoryRepo jobAdHistoryRepo) {
        this.jobAdHistoryRepo = jobAdHistoryRepo;
    }

    public void save(List<JobAd> dto) {
        List<JobAdHistoryEntity> entities = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (JobAd ad : dto) {
            entities.add(JobAdHistoryEntity.builder()
                    .title(ad.getTitle())
                    .company(ad.getFirmaName())
                    .link(ad.getUrl())
                    .location(ad.getLocation())
                    .description(ad.getDescription())
                    .time(now)
                    .build());
        }
        jobAdHistoryRepo.saveAll(entities);
    }



    @Transactional(readOnly = true)
    public boolean checkIfRecordExists(String link, String description) {
        LocalDateTime fourteenDaysAgo = LocalDateTime.now().minusDays(14);
        return jobAdHistoryRepo.existsByLinkOrDescriptionAndTimeAfter(link, description, fourteenDaysAgo);
    }
}
