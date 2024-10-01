package com.go.denys.selenium.automatization.linkedin.jobs.scaner.dto;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.enums.TimeRangeSelector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
public class ScannerFilter {

    private Optional<String> keywords;
    private Optional<String> location;
    private Optional<TimeRangeSelector> timeRange;
    private boolean uniqueness;

    public ScannerFilter() {
        uniqueness = true;
    }
}
