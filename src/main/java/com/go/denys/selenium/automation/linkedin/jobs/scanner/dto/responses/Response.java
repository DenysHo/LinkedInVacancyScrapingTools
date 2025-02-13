package com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.responses;

import com.go.denys.selenium.automation.linkedin.jobs.scanner.dto.JobAd;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
public abstract class Response<A extends JobAd> {

    private List<A> ads;
    private int adsFound;
    private int adsAfterFilter;
    private int adsShowed;
}
