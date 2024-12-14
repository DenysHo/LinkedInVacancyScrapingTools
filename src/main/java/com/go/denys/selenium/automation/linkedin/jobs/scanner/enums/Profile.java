package com.go.denys.selenium.automation.linkedin.jobs.scanner.enums;

public enum Profile {
    DE("Germany"),
    EN("English"),
    RU("Russian"),
    UK("Ukrainian");

    private String language;

    private Profile(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
