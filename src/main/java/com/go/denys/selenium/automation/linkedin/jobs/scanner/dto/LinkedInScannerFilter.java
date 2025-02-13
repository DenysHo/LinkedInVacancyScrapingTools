package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.enums.TimeRangeSelector;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class LinkedInScannerFilter extends ScannerFilter {

    @Builder.Default
    private String location = "";
    @Builder.Default
    private TimeRangeSelector timeRange = TimeRangeSelector.DAY;
    private String linkedInUserLogin;
    private String linkedInUserPassword;

}
