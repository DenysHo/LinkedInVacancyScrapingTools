package com.go.denys.selenium.automatization.linkedin.jobs.scaner.repo;

import com.go.denys.selenium.automatization.linkedin.jobs.scaner.entity.JobAdHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface JobAdHistoryRepo extends JpaRepository<JobAdHistoryEntity, Long> {
    List<JobAdHistoryEntity> findByLink(String link);

    boolean existsByLink(String link);

    boolean existsByDescription(String description);

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END " +
            "FROM JobAdHistoryEntity j " +
            "WHERE (j.link = :link OR j.description = :description) " +
            "AND j.time >= :time")
    boolean existsByLinkOrDescriptionAndTimeAfter(@Param("link") String link,
                                                  @Param("description") String description,
                                                  @Param("time") LocalDateTime time);
}
