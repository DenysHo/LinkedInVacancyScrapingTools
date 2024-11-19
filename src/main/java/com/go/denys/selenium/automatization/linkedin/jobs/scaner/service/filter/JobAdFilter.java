package com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.filter;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.service.db.JobAdHistoryService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.JobLevel.*;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.Profile.*;
import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.util.JobAdUtil.print;

@Service
@Data
@NoArgsConstructor
public class JobAdFilter<F extends ScannerFilter, A extends JobAd> {

    private static final Logger logger = LoggerFactory.getLogger(JobAdFilter.class);

    @Value("${profiles.path}")
    private static String profilesPath;
    private JobAdHistoryService jobAdHistoryService;
    protected int adsCountForLogging;

    @Autowired
    public JobAdFilter(JobAdHistoryService jobAdHistoryService) {
        this.jobAdHistoryService = jobAdHistoryService;
    }

    public List<A> filter(List<A> jobs, F filter) {
        adsCountForLogging = jobs.size();
        List<A> result = filterPosition(jobs);
        logFilter("Position", result.size());
        //print(new ArrayList<>(result));
        result = filterGermanJobs(result, filter);
        logFilter("German", result.size());
        //print(new ArrayList<>(result));
        result = filterDescription(result);
        logFilter("Description", result.size());
        //print(new ArrayList<>(result));
        if (filter.isUniqueness()) {
            result = filterDuplicates(result);
            logFilter("Uniqueness", result.size());
            //print(new ArrayList<>(result));
        }
        if (filter.isPrevious()) {
            result = filterByPreviousTimes(result);
            logFilter("Previous Times", result.size());
            //print(new ArrayList<>(result));
        }

        return result;
    }

    protected void logFilter(String filter, int adsCountAfterFiler) {
        String format = "Filtered %s: %s";
        logger.info(String.format(format, filter, adsCountForLogging - adsCountAfterFiler));
        adsCountForLogging = adsCountAfterFiler;
    }

    private List<A> filterByPreviousTimes(List<A> result) {
        result = result.stream()
                .filter(j -> !jobAdHistoryService.checkIfRecordExists(j.getUrl(), j.getDescription()))
                .toList();
        return result;
    }

    private List<A> filterDuplicates(List<A> result) {
        result = result.stream()
                .collect(Collectors.toMap(
                        JobAd::getUrl,  // use field Url as a key
                        obj -> obj,
                        (existing, replacement) -> existing))
                .values()
                .stream().toList();
        return result;
    }

    private List<A> filterDescription(List<A> result) {
        return result.stream()
                .filter(j -> {
                    if (j.getDescription().isEmpty()) {
                        return true;
                    }

                    String desc = j.getDescription().toLowerCase();

                    boolean noGermanSpeaking = (!desc.contains("german") || desc.contains("english") || desc.contains("germany")) && !desc.contains("german and english") && !desc.contains("english and german");
                    if (!noGermanSpeaking) {
                        logger.debug(String.format("Filtered %s required German", j.getId()));
                    }
                    boolean javaSpring = desc.matches(".*" + "java([^s]|$)" + ".*") || desc.contains("spring");
                    if (!javaSpring) {
                        logger.debug(String.format("Filtered %s not contains java or spring", j.getId()));
                    }

                    return noGermanSpeaking && javaSpring;
                })
                .toList();
    }

    private List<A> filterPosition(List<A> jobs) {
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

                logger.debug(String.format("Filtered %s by Position contains%s", j.getId(), builder));
            }
            return result;
        }).toList();
    }

    public List<A> filterGermanJobs(List<A> jobs, F filter) {
        return jobs.stream()
                .filter(job -> {
                    try {
                        if (job.getDescription() == null || job.getDescription().isEmpty()) {
                            return true;
                        }
                        Detector detector = DetectorFactory.create();
                        detector.append(job.getDescription());

                        List<Language> languages = detector.getProbabilities();

                        Map<String, Boolean> languageChecks = new HashMap<>();
                        languageChecks.put("en", filter.getProfiles().contains(EN));
                        languageChecks.put("ru", filter.getProfiles().contains(RU));
                        languageChecks.put("uk", filter.getProfiles().contains(UK));

                        return languageChecks.entrySet().stream()
                                .anyMatch(entry -> entry.getValue() &&
                                        languages.stream()
                                                .anyMatch(lang -> lang.lang.equals(entry.getKey()) && lang.prob > 0.2));

                    } catch (LangDetectException e) {
                        logger.info("An error occurred: {}", e.getMessage(), e);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
