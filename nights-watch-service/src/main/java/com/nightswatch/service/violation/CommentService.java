package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.Comment;
import com.nightswatch.service.CrudService;

import java.util.List;

public interface CommentService extends CrudService<Comment> {
    List<Comment> findByViolationId(Long violationId);
}
