package com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.TimeRangeSelector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScannerFilter {

    @Builder.Default
    private String keywords = "";
    @Builder.Default
    private String location = "";
    @Builder.Default
    private TimeRangeSelector timeRange = TimeRangeSelector.DAY;
    @Builder.Default
    private boolean uniqueness = true;
    @Builder.Default
    private boolean previous = true;
    @Builder.Default
    private int limit = 0;

}
