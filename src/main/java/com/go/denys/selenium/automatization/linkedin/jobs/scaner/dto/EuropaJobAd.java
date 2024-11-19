package com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto;


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
