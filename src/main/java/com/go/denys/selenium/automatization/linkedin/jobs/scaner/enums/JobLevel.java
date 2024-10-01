package com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums;

public enum JobLevel {
    JUNIOR("junior"),
    MIDDLE("middle"),
    SENIOR("senior"),
    ARCHITECT("architect"),
    LEAD("lead");

    private final String level;

    JobLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level;
    }
}
