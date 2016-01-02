package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.ViolationProperty;
import com.nightswatch.dal.repository.violation.ViolationPropertyRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.ViolationPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ViolationPropertyServiceImpl extends AbstractService<ViolationProperty, ViolationPropertyRepository> implements ViolationPropertyService {

    @Autowired
    public ViolationPropertyServiceImpl(final ViolationPropertyRepository repository) {
        super(repository);
    }
}
