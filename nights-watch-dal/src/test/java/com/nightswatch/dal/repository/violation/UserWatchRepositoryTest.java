package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.UserWatch;
import com.nightswatch.dal.entity.violation.Violation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class UserWatchRepositoryTest extends AbstractViolationBasedRepositoryTest {

    @Autowired
    private UserWatchRepository userWatchRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        final User user = this.createTestUser("USER_WATCH_TEST_1");
        final Violation violation = this.createTestViolation(user, "USER_WATCH_VIOLATION_1", "USER_WATCH_GROUP_1");
        UserWatch userWatch = new UserWatch();
        userWatch.setUser(user);
        userWatch.setViolation(violation);
        userWatch = this.userWatchRepository.save(userWatch);
        assertNotNull(userWatch);
        assertNotNull(userWatch.getId());

        final UserWatch dbUserWatch = this.userWatchRepository.findOne(userWatch.getId());
        assertNotNull(dbUserWatch);
        assertEquals(userWatch, dbUserWatch);
    }

    @Test
    public void testFindByViolationId() throws Exception {
        final User user = this.createTestUser("USER_WATCH_TEST_2");
        final Violation violation = this.createTestViolation(user, "USER_WATCH_VIOLATION_2", "USER_WATCH_GROUP_2");
        UserWatch userWatch = new UserWatch();
        userWatch.setUser(user);
        userWatch.setViolation(violation);
        userWatch = this.userWatchRepository.save(userWatch);
        assertNotNull(userWatch);
        assertNotNull(userWatch.getId());

        final List<UserWatch> userWatchs = this.userWatchRepository.findByViolationId(violation.getId());
        assertNotNull(userWatchs);
        assertFalse(userWatchs.isEmpty());
        assertEquals(userWatch, userWatchs.get(0));

    }
}