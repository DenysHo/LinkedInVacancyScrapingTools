package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScanResult<A extends JobAd> {

    private int adsFound;
    private int adsAfterFilter;
    private List<A> ads;
}
