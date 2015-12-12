package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.violation.Comment;
import com.nightswatch.dal.entity.violation.Violation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Comment ile ilgili testlerin calisabilmesi icin User ve Violation testlerinin basarili ile sonuclanması gerekiyor.
 */
public class CommentRepositoryTest extends AbstractViolationBasedRepositoryTest {

    @Autowired
    private CommentRepository commentRepository; // SUT

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

}