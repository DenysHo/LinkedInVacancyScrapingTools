package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class EuropaJobAd extends JobAd{

    @Builder.Default
    private String languages = "";

}
