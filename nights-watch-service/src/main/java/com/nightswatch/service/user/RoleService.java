package com.nightswatch.service.user;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.service.CrudService;

import java.util.Collection;

public interface RoleService extends CrudService<Role> {

    Collection<Role> getBasicUserRoles();
}
