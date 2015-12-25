package com.nightswatch.service.user.impl;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.repository.user.RoleRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.user.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RoleServiceImpl extends AbstractService<Role, RoleRepository> implements RoleService {

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        super(repository);
    }

    @Override
    public Collection<Role> getBasicUserRoles() {
        final Role role = this.repository.findByRoleName("BASIC");
        return Collections.singletonList(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return repository.findByRoleName(roleName);
    }
}
