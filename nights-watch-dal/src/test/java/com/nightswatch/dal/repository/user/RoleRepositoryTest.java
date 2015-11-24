package com.nightswatch.dal.repository.user;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class RoleRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testSaveRole() throws Exception {
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_NAME");

        roleRepository.save(role);

        Assert.assertNotNull(role);
        Assert.assertNotNull(role.getId());

    }

    @Test
    public void testFindByRoleName() throws Exception {
        final Role role = new Role();
        role.setRoleName("BASIC");
        roleRepository.save(role);

        Assert.assertNotNull(role);
        Assert.assertNotNull(role.getId());

        final Role dbRole = roleRepository.findByRoleName("BASIC");
        Assert.assertEquals(role, dbRole);

    }
}