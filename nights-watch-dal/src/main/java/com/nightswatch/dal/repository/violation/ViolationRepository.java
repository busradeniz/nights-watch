package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViolationRepository extends JpaRepository<Violation, Long> {

    @Query("SELECT v " +
            "FROM Violation v " +
            "WHERE v.owner = :owner " +
            "AND (v.violationStatusType IN (:violationStatusTypes) )" +
            "ORDER BY v.violationDate DESC")
    List<Violation> findAllByOwnerAndViolationStatusType(@Param("owner") final User owner,
                                                         @Param("violationStatusTypes") final ViolationStatusType[] violationStatusTypes);

    @Query("SELECT v " +
            "FROM Violation v " +
            "JOIN v.userWatches uw " +
            "WHERE uw.user = :owner " +
            "AND (v.violationStatusType IN (:violationStatusTypes) )" +
            "ORDER BY v.violationDate DESC")
    List<Violation> findAllWatchedViolations(@Param("owner") final User owner,
                                             @Param("violationStatusTypes") final ViolationStatusType[] violationStatusTypes);

    @Query("SELECT v " +
            "FROM Violation v " +
            "ORDER BY size(v.userLikes) DESC")
    List<Violation> findAllWithMostLikes();
}
