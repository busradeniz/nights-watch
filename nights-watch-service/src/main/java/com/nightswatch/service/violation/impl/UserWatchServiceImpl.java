package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.UserWatch;
import com.nightswatch.dal.repository.violation.UserWatchRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.UserWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserWatchServiceImpl extends AbstractService<UserWatch, UserWatchRepository> implements UserWatchService {

    @Autowired
    public UserWatchServiceImpl(UserWatchRepository repository) {
        super(repository);
    }

    @Override
    public List<UserWatch> findByViolationId(Long violationId) {
        return repository.findByViolationId(violationId);
    }
}
