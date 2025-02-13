package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.requests;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.LinkedInScannerFilter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class LikedInRequest extends Request<LinkedInScannerFilter> {
}
