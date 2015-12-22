package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.UserLike;
import com.nightswatch.dal.entity.violation.Violation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserLikeRepositoryTest extends AbstractViolationBasedRepositoryTest {

    @Autowired
    private UserLikeRepository userLikeRepository; // SUT

    @Test
    public void testSaveAndGet() throws Exception {
        final User user = this.createTestUser("USER_LIKE_TEST_1");
        final Violation violation = this.createTestViolation(user, "USER_LIKE_VIOLATION_1", "USER_LIKE_GROUP_1");
        UserLike userLike = new UserLike();
        userLike.setUser(user);
        userLike.setViolation(violation);
        userLike.setLikeDate(new Date());
        userLike = this.userLikeRepository.save(userLike);
        assertNotNull(userLike);
        assertNotNull(userLike.getId());

        final UserLike dbUserLike = this.userLikeRepository.findOne(userLike.getId());
        assertNotNull(dbUserLike);
        assertEquals(userLike, dbUserLike);
    }

    @Test
    public void testFindByViolationId() throws Exception {
        final User user = this.createTestUser("USER_LIKE_TEST_2");
        final Violation violation = this.createTestViolation(user, "USER_LIKE_VIOLATION_2", "USER_LIKE_GROUP_2");
        UserLike userLike = new UserLike();
        userLike.setUser(user);
        userLike.setViolation(violation);
        userLike.setLikeDate(new Date());
        userLike = this.userLikeRepository.save(userLike);
        assertNotNull(userLike);
        assertNotNull(userLike.getId());

        final List<UserLike> userLikes = this.userLikeRepository.findByViolationId(violation.getId());
        assertNotNull(userLikes);
        assertFalse(userLikes.isEmpty());
        assertEquals(userLike, userLikes.get(0));

    }
}