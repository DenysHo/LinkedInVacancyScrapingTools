package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LinkedInResponse extends Response<JobAd> {
}
