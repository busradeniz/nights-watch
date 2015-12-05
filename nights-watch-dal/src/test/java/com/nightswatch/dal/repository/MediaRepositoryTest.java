package com.nightswatch.dal.repository;

import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MediaRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    public void testSaveAndRead() throws Exception {
        Media media = new Media();
        media.setMediaType(MediaType.IMAGE);
        media.setUrl("TEST_URL");

        media = mediaRepository.save(media);
        assertNotNull(media);
        assertNotNull(media.getId());


        final Media dbMedia = mediaRepository.findOne(media.getId());
        assertEquals(media, dbMedia);
    }
}