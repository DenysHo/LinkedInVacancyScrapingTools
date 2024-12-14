package com.go.denys.selenium.automation.linkedin.jobs.scanner.enums;

public enum TimeRangeSelector {
    DAY("f_TPR-3"),
    WEEK("f_TPR-2"),
    MONTH("f_TPR-1"),
    ANY_TIME("f_TPR-0");


    private String id;

    private TimeRangeSelector(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TimeRangeSelector{" +
                "id='" + id + '\'' +
                '}';
    }
}
