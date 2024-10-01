package com.go.denys.selenium.automatization.linkedin.jobs.scaner.excel;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JobAdsExcelWriter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH-mm");
    private static final List<String> headers;

    static {
        headers = new ArrayList<>();
        headers.add("Company Name");
        headers.add("Position");
        headers.add("Location");
        headers.add("Date");
        headers.add("Status");
        headers.add("URL");
    }

    public void write (List<JobAd> ads) {
        JobAdToExcelWritingTransformer transformer = new JobAdToExcelWritingTransformer();
        List<List<String>> data = transformer.transform(ads);

        LocalDateTime now = LocalDateTime.now();

        String formattedDateTime = now.format(FORMATTER);

        ExcelWriter writer = new ExcelWriter(data, headers, "C:\\Users\\gluza\\OneDrive\\Рабочий стол\\jobs.xlsx", formattedDateTime);
        writer.write();
    }
}