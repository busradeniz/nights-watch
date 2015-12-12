package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.UserLike;
import com.nightswatch.dal.repository.violation.UserLikeRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.UserLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserLikeServiceImpl extends AbstractService<UserLike, UserLikeRepository> implements UserLikeService {

    @Autowired
    public UserLikeServiceImpl(UserLikeRepository repository) {
        super(repository);
    }

    @Override
    public List<UserLike> findByViolationId(Long violationId) {
        return repository.findByViolationId(violationId);
    }
}
