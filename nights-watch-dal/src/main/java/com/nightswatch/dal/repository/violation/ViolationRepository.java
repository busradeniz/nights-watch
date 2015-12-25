package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Long> {

    List<Violation> findAllByOwnerAndViolationStatusType(final User owner, final ViolationStatusType violationStatusType);

    @Query("SELECT v " +
            "FROM Violation v " +
            "JOIN v.userWatches uw " +
            "WHERE uw.user = :owner " +
            "AND (:violationStatusType IS NULL OR v.violationStatusType = :violationStatusType )" +
            "ORDER BY v.violationDate DESC")
    List<Violation> findAllWatchedViolations(@Param("owner") final User owner,
                                             @Param("violationStatusType") final ViolationStatusType violationStatusType);

    @Query("SELECT v " +
            "FROM Violation v " +
            "ORDER BY size(v.userLikes) DESC")
    List<Violation> findAllWithMostLikes();
}
