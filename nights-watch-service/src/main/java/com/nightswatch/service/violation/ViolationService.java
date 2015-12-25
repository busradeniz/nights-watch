package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import com.nightswatch.service.CrudService;

import java.util.List;

public interface ViolationService extends CrudService<Violation> {
    List<Violation> findAllNewest();

    List<Violation> findAllByOwnerAndViolationStatusType(User owner, ViolationStatusType violationStatusType);

    List<Violation> findAllWatchedViolations(User owner, ViolationStatusType violationStatusType);

    List<Violation> findAllWithMostLikes();
}
