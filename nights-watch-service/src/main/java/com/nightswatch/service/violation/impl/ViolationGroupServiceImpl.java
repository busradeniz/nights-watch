package com.nightswatch.service.violation.impl;

import com.nightswatch.dal.entity.violation.ViolationGroup;
import com.nightswatch.dal.repository.violation.ViolationGroupRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.violation.ViolationGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ViolationGroupServiceImpl extends AbstractService<ViolationGroup, ViolationGroupRepository> implements ViolationGroupService {

    @Autowired
    public ViolationGroupServiceImpl(ViolationGroupRepository repository) {
        super(repository);
    }
}
