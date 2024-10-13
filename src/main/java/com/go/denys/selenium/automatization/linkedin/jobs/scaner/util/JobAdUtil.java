package com.go.denys.selenium.automatization.linkedin.jobs.scaner.util;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;

import java.util.List;

public class JobAdUtil {

    private static final String PATTERN = "%s, %s, %s;";
    public static final String TEST_PATTERN = "jobs.add(new JobAd(\"%s\", \"%s\", \"%s\", %n\"%s\"));";

    public static void print(List<JobAd> ads) {
        for(JobAd ad : ads) {
            print(ad);
        }
    }

    public static void printForTest(List<JobAd> ads) {
        for(JobAd ad : ads) {
            printForTest(ad);
        }
    }

    public static void print(JobAd ad) {
        System.out.printf((PATTERN) + "%n", ad.getUrl(), ad.getFirmaName(), ad.getTitle());
    }

    public static void printForTest(JobAd ad) {
        System.out.printf((TEST_PATTERN) + "%n",
                ad.getId(),
                ad.getTitle(),
                ad.getUrl().replaceAll("\"", ""),
                ad.getDescription().replaceAll("[\\r\\n]", "").replaceAll("\"", "")
                );
    }
}
