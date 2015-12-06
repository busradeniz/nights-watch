package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.*;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import com.nightswatch.dal.repository.user.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ViolationRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViolationGroupRepository violationGroupRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        final User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName("TEST_VIOLATION_GROUP_NAME");
        violationGroupRepository.save(violationGroup);


        Violation violation = new Violation();
        violation.setTitle("TEST_VIOLATION");
        violation.setFrequencyLevelType(FrequencyLevelType.HIGH);
        violation.setDangerLevelType(DangerLevelType.HIGH);
        violation.setViolationStatusType(ViolationStatusType.NOT_VIOLATION);
        violation.setAddress("TEST_ADDRESS");
        violation.setDescription("TEST_DESCRIPTION");
        violation.setLastModifiedBy("LAST_MODIFIED_USER");
        violation.setLastModifiedDate(new Date());
        violation.setLatitude(0d);
        violation.setLongitude(0d);
        violation.setViolationDate(new Date());
        violation.setOwner(user);
        violation.setViolationGroup(violationGroup);
        violation = violationRepository.save(violation);
        assertNotNull(violation);
        assertNotNull(violation.getId());

        final Violation dbViolation = violationRepository.findOne(violation.getId());
        assertEquals(violation, dbViolation);

    }
}