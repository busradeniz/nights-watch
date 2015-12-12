package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {

    @Query("SElECT ul FROM UserLike ul WHERE ul.violation.id=:violationId")
    List<UserLike> findByViolationId(@Param("violationId") final Long violationId);
}
