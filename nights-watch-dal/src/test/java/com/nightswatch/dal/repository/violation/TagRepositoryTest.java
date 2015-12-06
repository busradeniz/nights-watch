package com.nightswatch.dal.repository.violation;

import com.nightswatch.dal.entity.violation.Tag;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TagRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testSaveAndGet() throws Exception {
        Tag tag = new Tag();
        tag.setName("TEST_TAG");
        tag = tagRepository.save(tag);
        assertNotNull(tag);
        assertNotNull(tag.getId());


        final Tag dbTag = tagRepository.findOne(tag.getId());
        assertNotNull(dbTag);
        assertEquals(tag, dbTag);
    }

    @Test
    public void testFindByName() throws Exception {
        Tag tag = new Tag();
        tag.setName("TEST_TAG_1");
        tag = tagRepository.save(tag);
        assertNotNull(tag);
        assertNotNull(tag.getId());


        final Tag dbTag = tagRepository.findByName("TEST_TAG_1");
        assertNotNull(dbTag);
        assertEquals(tag, dbTag);
    }


}