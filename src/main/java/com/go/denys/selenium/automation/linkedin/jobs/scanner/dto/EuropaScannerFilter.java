package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.enums.Location;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
public class EuropaScannerFilter extends ScannerFilter {

    @Builder.Default
    private List<Location> locations = new ArrayList<>();
    @Builder.Default
    private List<String> languages = new ArrayList<>();


}
