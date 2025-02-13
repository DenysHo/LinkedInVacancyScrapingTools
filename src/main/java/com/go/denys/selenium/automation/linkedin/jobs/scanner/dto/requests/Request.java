package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.requests;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.ScannerFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public abstract class Request<F extends ScannerFilter> {

    private F filter;
}
