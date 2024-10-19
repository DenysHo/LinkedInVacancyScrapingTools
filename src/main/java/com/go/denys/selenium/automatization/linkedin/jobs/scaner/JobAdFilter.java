package com.go.denys.selenium.automatization.linkedin.jobs.scaner;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.JobAdHistoryService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.JobLevel.*;

@Service
@Data
@NoArgsConstructor
public class JobAdFilter {

    private static final Logger logger = LoggerFactory.getLogger(JobAdFilter.class);

    @Value("${profiles.path}")
    private static String profilesPath;
    private JobAdHistoryService jobAdHistoryService;

    @Autowired
    public JobAdFilter(JobAdHistoryService jobAdHistoryService) {
        this.jobAdHistoryService = jobAdHistoryService;
    }

    static {
        try {
            DetectorFactory.loadProfile("src/main/resources/profiles"); // path to languages profiles
        } catch (LangDetectException e) {
            e.printStackTrace();
        }
    }

    public List<JobAd> filter(List<JobAd> jobs, ScannerFilter filter) {
        List<JobAd> result = filterPosition(jobs);
        result = filterGermanJobs(result);
        result = filterDescription(result);
        if (filter.isUniqueness()) {
            result = filterDuplicates(result);
        }
        if (filter.isPrevious()) {
            result = filterByPreviousTimes(result);
        }

        return result;
    }

    private List<JobAd> filterByPreviousTimes(List<JobAd> result) {
        result = result.stream()
                .filter(j -> !jobAdHistoryService.checkIfRecordExists(j.getUrl(), j.getDescription()))
                .toList();
        return result;
    }

    private List<JobAd> filterDuplicates(List<JobAd> result) {
        result = result.stream()
                .collect(Collectors.toMap(
                        JobAd::getUrl,  // use field Url as a key
                        obj -> obj,
                        (existing, replacement) -> existing))
                .values()
                .stream().toList();
        return result;
    }

    private List<JobAd> filterDescription(List<JobAd> result) {
        return result.stream()
                .filter(j -> {
                    String desc = j.getDescription().toLowerCase();

                    boolean noGermanSpeaking = (!desc.contains("german") || desc.contains("english") || desc.contains("germany")) && !desc.contains("german and english") && !desc.contains("english and german");
                    if (!noGermanSpeaking) {
                        logger.info(String.format("Filtered %s required German", j.getId()));
                    }
                    boolean javaSpring = desc.matches(".*" + "java([^s]|$)" + ".*") || desc.contains("spring");
                    if (!javaSpring) {
                        logger.info(String.format("Filtered %s not contains java or spring", j.getId()));
                    }

                    return noGermanSpeaking && javaSpring;
                })
                .toList();
    }

    private List<JobAd> filterPosition(List<JobAd> jobs) {
        return jobs.stream().filter(j -> {
            String title = j.getTitle().toLowerCase();
            boolean senior = title.contains(SENIOR.getLevel());
            boolean architect = title.contains(ARCHITECT.getLevel());
            boolean teamLead = title.contains(TEAM_LEAD.getLevel());
            //boolean engineer = title.contains(ENGINEER.getLevel());
            boolean middle = title.contains(MIDDLE.getLevel());
            boolean junior = title.contains(JUNIOR.getLevel());
            //boolean developer = title.contains(DEVELOPER.getLevel());
            boolean result = !senior && !architect && !teamLead || middle || junior;

            if (!result) {
                StringBuilder builder = new StringBuilder();
                if (senior) {
                    builder.append(" ").append(SENIOR.getLevel());
                } else if (architect) {
                    builder.append(" ").append(ARCHITECT.getLevel());
                } else if (teamLead) {
                    builder.append(" ").append(TEAM_LEAD.getLevel());
                } /*else if (lead) {
                    builder.append(" ").append(LEAD.getLevel());
                } else if (engineer) {
                    builder.append(" ").append(ENGINEER.getLevel());
                }*/

                logger.info(String.format("Filtered %s by Position contains%s", j.getId(), builder));
            }
            return result;
        }).toList();
    }

    public List<JobAd> filterGermanJobs(List<JobAd> jobs) {
        return jobs.stream()
                .filter(job -> {
                    try {
                        if (job.getDescription() == null || job.getDescription().isEmpty()) {
                            return true;
                        }
                        Detector detector = DetectorFactory.create();
                        detector.append(job.getDescription());

                        List<Language> languages = detector.getProbabilities();

                        boolean containsEnglish = languages.stream()
                                .anyMatch(lang -> lang.lang.equals("en") && lang.prob > 0.2);

                        return containsEnglish;

                    } catch (LangDetectException e) {
                        e.printStackTrace();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
