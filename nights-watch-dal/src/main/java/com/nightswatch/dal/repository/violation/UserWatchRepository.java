package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.UserWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserWatchRepository extends JpaRepository<UserWatch, Long> {

    @Query("SELECT uw FROM UserWatch uw WHERE uw.violation.id=:violationId")
    List<UserWatch> findByViolationId(@Param("violationId") final Long violationId);
}
