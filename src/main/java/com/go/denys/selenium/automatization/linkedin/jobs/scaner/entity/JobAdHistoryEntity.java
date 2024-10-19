package com.go.denys.selenium.automatization.linkedin.jobs.scaner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_ad_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobAdHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    private String title;
    private String description;
    private String link;
    private String company;
    private String location;

}
