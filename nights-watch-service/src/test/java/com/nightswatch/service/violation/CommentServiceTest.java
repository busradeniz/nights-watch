package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.Comment;
import com.nightswatch.dal.repository.violation.CommentRepository;
import com.nightswatch.service.violation.impl.CommentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void testFindByViolationId() throws Exception {
        final Comment expected = new Comment();

        when(commentRepository.findByViolationId(101L))
                .thenReturn(Collections.singletonList(expected));

        final List<Comment> comments = commentService.findByViolationId(101L);
        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(expected, comments.get(0));
    }
}