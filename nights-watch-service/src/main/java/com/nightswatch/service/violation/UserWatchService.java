package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.UserWatch;
import com.nightswatch.service.CrudService;

import java.util.List;

public interface UserWatchService extends CrudService<UserWatch> {
    List<UserWatch> findByViolationId(Long violationId);
}
