package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.EuropaJobAd;
import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EuropaResponse extends Response<EuropaJobAd> {

}
