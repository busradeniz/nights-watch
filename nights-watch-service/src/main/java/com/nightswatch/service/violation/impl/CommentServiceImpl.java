package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.Comment;
import com.nightswatch.dal.repository.violation.CommentRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.CommentService;
import com.nightswatch.service.violation.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CommentServiceImpl extends AbstractService<Comment, CommentRepository> implements CommentService {

    private final ViolationService violationService;

    @Autowired
    public CommentServiceImpl(final CommentRepository repository,
                              final ViolationService violationService) {
        super(repository);
        this.violationService = violationService;
    }

    @Override
    public List<Comment> findByViolationId(Long violationId) {
        return repository.findByViolationId(violationId);
    }
}
