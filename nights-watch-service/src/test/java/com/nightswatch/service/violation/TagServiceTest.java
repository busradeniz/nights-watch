package com.nightswatch.service.violation;

import com.nightswatch.dal.entity.violation.Tag;
import com.nightswatch.dal.repository.violation.TagRepository;
import com.nightswatch.service.exception.IllegalTagNameException;
import com.nightswatch.service.violation.impl.TagServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    public void testFindAndNotCreate() throws Exception {
        final String tagName = "my-tag";
        final Tag expected = new Tag();
        expected.setName(tagName);

        when(tagRepository.findByName(tagName))
                .thenReturn(expected);

        final Tag actual = tagService.findOrCreate(tagName);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(tagRepository).findByName(tagName);
        verify(tagRepository, times(0)).save(any(Tag.class));
    }

    @Test
    public void testNotFindAndCreate() throws Exception {
        final String tagName = "my-tag";
        final Tag expected = new Tag();
        expected.setName(tagName);

        when(tagRepository.findByName(tagName))
                .thenReturn(null);
        when(tagRepository.save(any(Tag.class)))
                .thenReturn(expected);

        final Tag actual = tagService.findOrCreate(tagName);
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(tagRepository).findByName(tagName);
        verify(tagRepository).save(any(Tag.class));
    }

    @Test(expected = IllegalTagNameException.class)
    public void testFindAndCreateWithEmptyTag() throws Exception {
        tagService.findOrCreate(null);
    }

    @Test
    public void testFindAllOrCreate() throws Exception {
        final String tagName1 = "my-tag-1";
        final Tag expected1 = new Tag();
        expected1.setName(tagName1);

        final String tagName2 = "my-tag-2";
        final Tag expected2 = new Tag();
        expected2.setName(tagName2);

        when(tagRepository.findByName(tagName1))
                .thenReturn(null);
        when(tagRepository.save(any(Tag.class)))
                .thenReturn(expected1);
        when(tagRepository.findByName(tagName2))
                .thenReturn(expected2);

        final Collection<Tag> tags = tagService.findAllOrCreate(tagName1, tagName2);
        assertThat(tags, Matchers.contains(expected1, expected2));

    }
}