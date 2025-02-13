package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.enums.Profile;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.enums.TimeRangeSelector;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
public abstract class ScannerFilter {

    @Builder.Default
    private String keywords = "";
    @Builder.Default
    private boolean uniqueness = true;
    @Builder.Default
    private boolean previous = true;
    @Builder.Default
    private int limit = 0;
    @Builder.Default
    private List<Profile> profiles = new ArrayList<>();

}
