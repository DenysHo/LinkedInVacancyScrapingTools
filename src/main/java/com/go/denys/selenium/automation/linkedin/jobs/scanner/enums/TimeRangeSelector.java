package com.go.denys.selenium.automation.linkedin.jobs.scanner.enums;

public enum TimeRangeSelector {
    DAY("f_TPR-3", "r86400"),
    WEEK("f_TPR-2", "r604800"),
    MONTH("f_TPR-1", "r2592000"),
    ANY_TIME("f_TPR-0");


    private String id;
    private String range;

    private TimeRangeSelector(String id, String range) {
        this.id = id;
        this.range = range;
    }

    private TimeRangeSelector(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getRange() {return range;}

    @Override
    public String toString() {
        return "TimeRangeSelector{" +
                "id='" + id + '\'' +
                '}';
    }
}
