package com.go.denys.selenium.automatization.linkedin.jobs.scaner;

import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;
import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.ScannerFilter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

import static com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.JobLevel.*;

public class JobAdFilter {

    @Value("${profiles.path}")
    private static String profilesPath;

    private ScannerFilter filter;

    public JobAdFilter(ScannerFilter filter) {
        this.filter = filter;
    }

    static {
        try {
            //System.out.println(profilesPath);

            DetectorFactory.loadProfile("src/main/resources/profiles"); // Путь к языковым профилям

        } catch (LangDetectException e) {
            e.printStackTrace();
        }
    }

    public List<JobAd> filter(List<JobAd> jobs) {
        List<JobAd> result = filterTitle(jobs);
        result = filterGermanJobs(result);
        result = result.stream()
                .filter(j -> {
                    String desc = j.getDescription().toLowerCase();

                    boolean noGermanSpeaking = (!desc.contains("german") || desc.contains("english")) && !desc.contains("german and english") && !desc.contains("english and german");
                    boolean javaSpring = desc.matches(".*" + "java([^s]|$)" + ".*") || desc.contains("spring");

                    return noGermanSpeaking && javaSpring;
                })
                .toList();
        //todo "+7 years of experience"
        //todo "At least 5 years"
        //todo "инженер"
        //todo "Android Java Developer"

        if (filter.isUniqueness())  {
            result = result.stream()
                    .collect(Collectors.toMap(
                            JobAd::getUrl,  // используем поле field в качестве ключа
                            obj -> obj,          // объект остается таким же
                            (existing, replacement) -> existing)) // обработка дубликатов
                    .values() // получаем уникальные значения
                    .stream().toList();
        }

        return result;
    }

    private static List<JobAd> filterTitle(List<JobAd> jobs) {
        return jobs.stream().filter(j -> {
            String title = j.getTitle().toLowerCase();
            return !title.contains(SENIOR.getLevel()) && !title.contains(ARCHITECT.getLevel()) && !title.contains(LEAD.getLevel())
                    || ((title.contains(SENIOR.getLevel()) || title.contains(ARCHITECT.getLevel()) || title.contains(LEAD.getLevel())) && (title.contains(MIDDLE.getLevel()) || title.contains(JUNIOR.getLevel())));
        }).toList();
    }

    // Метод для фильтрации объявлений, содержащих только немецкий или оба языка
    public List<JobAd> filterGermanJobs(List<JobAd> jobs) {
        return jobs.stream()
                .filter(job -> {
                    try {
                        if (job.getDescription() == null || job.getDescription().isEmpty()) {
                            return true;
                        }
                        Detector detector = DetectorFactory.create();
                        detector.append(job.getDescription());

                        // Получаем вероятные языки
                        List<Language> languages = detector.getProbabilities();

                        // Проверяем, есть ли немецкий язык в вероятных языках
                        boolean containsGerman = languages.stream()
                                .anyMatch(lang -> lang.lang.equals("de") && lang.prob > 0.2);  // например, вероятность выше 20%

                        // Проверяем, есть ли английский язык в вероятных языках
                        boolean containsEnglish = languages.stream()
                                .anyMatch(lang -> lang.lang.equals("en") && lang.prob > 0.2);

                        return containsEnglish;

                    } catch (LangDetectException e) {
                        //e.printStackTrace();
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
