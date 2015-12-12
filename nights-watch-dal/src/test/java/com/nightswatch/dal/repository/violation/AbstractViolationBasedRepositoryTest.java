package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.*;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import com.nightswatch.dal.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.Random;

public abstract class AbstractViolationBasedRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ViolationRepository violationRepository;

    @Autowired
    protected ViolationGroupRepository violationGroupRepository;

    protected User createTestUser(final String username) {
        final User user = new User();
        user.setEmail(username + "@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("username");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_" + new Random().nextInt());
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    protected Violation createTestViolation(final User owned, final String title, final String violationGroupName) {
        final ViolationGroup violationGroup = new ViolationGroup();
        violationGroup.setName(violationGroupName);
        violationGroupRepository.save(violationGroup);


        Violation violation = new Violation();
        violation.setTitle(title);
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
        violation.setOwner(owned);
        violation.setViolationGroup(violationGroup);
        return violationRepository.save(violation);
    }
}
