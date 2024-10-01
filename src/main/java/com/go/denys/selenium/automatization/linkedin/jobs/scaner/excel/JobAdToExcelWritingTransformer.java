package com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JobAdToExcelWritingTransformer {

    public static final String GESCHICKT = "geschickt";
    public static final String PATTERN = "dd.MM.yyyy";

    public List<List<String>> transform(List<JobAd> ads) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        String formattedDate = currentDate.format(formatter);

        List<List<String>> result = new ArrayList<>();
        for (JobAd ad : ads) {
            List<String> list = new ArrayList<>();
            list.add(ad.getFirmaName());
            list.add(ad.getTitle());
            list.add(ad.getLocation());
            list.add(formattedDate);
            list.add(GESCHICKT);
            list.add(ad.getUrl());
            result.add(list);
        }

        return result;
    }
}
