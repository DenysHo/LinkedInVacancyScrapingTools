package com.go.denys.selenium.automation.linkedin.jobs.scanner.enums;

public enum PublicationPeriodEuropa {
    LAST_DAY("LAST_DAY"),
    LAST_THREE_DAYS("LAST_THREE_DAYS"),
    LAST_WEEK("LAST_WEEK"),
    LAST_MONTH("LAST_MONTH");

    private final String value;

    private PublicationPeriodEuropa(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
