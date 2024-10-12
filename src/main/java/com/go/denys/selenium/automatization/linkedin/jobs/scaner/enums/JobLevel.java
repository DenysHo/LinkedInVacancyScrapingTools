package com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums;

import java.util.List;

public enum JobLevel {
    JUNIOR("junior"),
    MIDDLE("middle"),
    SENIOR("senior"),
    ARCHITECT("architect"),
    LEAD("lead"),
    TEAM_LEAD("team lead"),
    ENGINEER("engineer"),
    DEVELOPER("developer");

    private final String level;

    JobLevel(String level) {
        this.level = level;
    }

    public static List<JobLevel> notAllowed() {
        return List.of(SENIOR, ARCHITECT, LEAD, ENGINEER);
    }

    public String getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level;
    }

    public boolean isJunior() {
        return this == JUNIOR;
    }

    public boolean isMiddle() {
        return this == MIDDLE;
    }

    public boolean isSenior() {
        return this == SENIOR;
    }

    public boolean isArchitect() {
        return this == ARCHITECT;
    }

    public boolean isLead() {
        return this == LEAD;
    }

    public boolean isEngineer() {
        return this == ENGINEER;
    }
}
