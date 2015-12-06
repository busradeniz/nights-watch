package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.Violation;
import com.nightswatch.dal.repository.violation.ViolationRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.ViolationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ViolationServiceImpl extends AbstractService<Violation, ViolationRepository> implements ViolationService {

    @Autowired
    public ViolationServiceImpl(ViolationRepository repository) {
        super(repository);
    }
}
