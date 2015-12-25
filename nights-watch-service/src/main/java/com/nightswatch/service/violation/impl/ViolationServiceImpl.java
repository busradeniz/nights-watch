package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.dal.entity.violation.ViolationStatusType;
import com.nightswatch.dal.repository.violation.ViolationRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ViolationServiceImpl extends AbstractService<Violation, ViolationRepository> implements ViolationService {

    @Autowired
    public ViolationServiceImpl(ViolationRepository repository) {
        super(repository);
    }

    @Override
    public List<Violation> findAllNewest() {
        return this.repository.findAll(new Sort(new Sort.Order(Sort.Direction.DESC, "violationDate")));
    }

    @Override
    public List<Violation> findAllByOwnerAndViolationStatusType(User owner, ViolationStatusType violationStatusType) {
        return repository.findAllByOwnerAndViolationStatusType(owner, violationStatusType);
    }

    @Override
    public List<Violation> findAllWatchedViolations(final User owner, ViolationStatusType violationStatusType) {
        return repository.findAllWatchedViolations(owner, violationStatusType);
    }

    @Override
    public List<Violation> findAllWithMostLikes() {
        return repository.findAllWithMostLikes();
    }
}
