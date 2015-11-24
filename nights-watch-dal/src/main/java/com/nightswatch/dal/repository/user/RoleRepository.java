package com.nightswatch.dal.repository.user;

import com.nightswatch.dal.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}
