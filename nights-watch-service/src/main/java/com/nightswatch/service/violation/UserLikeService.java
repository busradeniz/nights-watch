package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.UserLike;
import com.nightswatch.service.CrudService;

import java.util.List;

public interface UserLikeService extends CrudService<UserLike> {
    List<UserLike> findByViolationId(Long violationId);
}
