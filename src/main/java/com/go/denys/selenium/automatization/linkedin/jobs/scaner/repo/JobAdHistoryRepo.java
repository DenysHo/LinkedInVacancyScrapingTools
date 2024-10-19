package com.go.denys.selenium.automatization.linkedin.jobs.scaner.repo;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.entity.JobAdHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface JobAdHistoryRepo extends JpaRepository<JobAdHistoryEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END " +
            "FROM JobAdHistoryEntity j " +
            "WHERE (j.link = :link OR j.description = :description) " +
            "AND j.time >= :time")
    boolean existsByLinkOrDescriptionAndTimeAfter(@Param("link") String link,
                                                  @Param("description") String description,
                                                  @Param("time") LocalDateTime time);
}
