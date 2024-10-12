package com.go.denys.selenium.automatization.linkedin.jobs.scaner.util;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto.JobAd;

public class JobAdUtil {

    private static final String PATTERN = "%s, %s, %s;";

    public static void print(JobAd ad) {
        System.out.printf((PATTERN) + "%n", ad.getUrl(), ad.getFirmaName(), ad.getTitle());
    }
}
