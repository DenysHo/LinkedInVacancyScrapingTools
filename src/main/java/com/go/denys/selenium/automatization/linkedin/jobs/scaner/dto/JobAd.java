package com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class JobAd {

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

    @Override
    public String toString() {
        return "JobAd{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", firmaName='" + firmaName + '\'' +
                ", url='" + url + '\'' +
                ", location='" + location + '\'' +
                '}' + '\n';
    }
}
