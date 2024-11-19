package com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public class JobAd {

    @Builder.Default
    private String id = "";
    @Builder.Default
    private String title = "";
    @Builder.Default
    private String description = "";
    @Builder.Default
    private String firmaName = "";
    @Builder.Default
    private String url = "";
    @Builder.Default
    private String location = "";

    public JobAd(String title, String description, String firmaName, String url, String location) {
        this.title = title;
        this.description = description;
        this.firmaName = firmaName;
        this.url = url;
        this.location = location;
    }

    public JobAd(String id, String title, String url, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public JobAd(long id, String title, String description, String sap, String url, String location) {
        this.id = id + "";
        this.title = title;
        this.description = description;
        this.firmaName = firmaName;
        this.url = url;
        this.location = location;
    }

    @Override
    public String toString() {
        return "JobAd{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", firmaName='" + firmaName + '\'' +
                ", url='" + url + '\'' +
                ", location='" + location + '\'' +
                '}' + '\n';
    }
}


