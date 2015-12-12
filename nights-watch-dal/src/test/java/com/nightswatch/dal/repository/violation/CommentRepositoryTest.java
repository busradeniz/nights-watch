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
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Comment ile ilgili testlerin calisabilmesi icin User ve Violation testlerinin basarili ile sonuclanması gerekiyor.
 */
public class CommentRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository commentRepository; // SUT

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private ViolationGroupRepository violationGroupRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        final User user = this.createTestUser("COMMENT_TEST_USER_1");
        final Violation violation = this.createTestViolation(user, "COMMENT_TEST_VIOLATION_1", "COMMENT_TEST_VIOLATION_GROUP_1");

        Comment comment = new Comment();
        comment.setCommentDate(new Date());
        comment.setContent("TEST_COMMENT_1");
        comment.setOwner(user);
        comment.setViolation(violation);
        comment = this.commentRepository.save(comment);
        assertNotNull(comment);
        assertNotNull(comment.getId());

        Comment dbComment = this.commentRepository.findOne(comment.getId());
        assertNotNull(dbComment);
        assertEquals(comment, dbComment);
    }

    @Test
    public void testFindByViolationId() throws Exception {
        final User user = this.createTestUser("COMMENT_TEST_USER_2");
        final Violation violation = this.createTestViolation(user, "COMMENT_TEST_VIOLATION_2", "COMMENT_TEST_VIOLATION_GROUP_2");

        Comment comment = new Comment();
        comment.setCommentDate(new Date());
        comment.setContent("TEST_COMMENT_2");
        comment.setOwner(user);
        comment.setViolation(violation);
        comment = this.commentRepository.save(comment);
        assertNotNull(comment);
        assertNotNull(comment.getId());

        final List<Comment> comments = this.commentRepository.findByViolationId(violation.getId());
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(comment, comments.get(0));
    }

    private User createTestUser(final String username) {
        final User user = new User();
        user.setEmail(username + "@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("username");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_" + new Random().nextInt());
        user.setRoles(Collections.singletonList(role));
        return userRepository.save(user);
    }

    public Violation createTestViolation(final User owned, final String title, final String violationGroupName) {
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