package com.nightswatch.service.impl;

import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;
import com.nightswatch.dal.repository.MediaRepository;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MediaServiceTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Mock
    private MediaRepository mediaRepository;

    @InjectMocks
    private MediaServiceImpl mediaService;

    @Test
    public void testCreateNewMedia() throws Exception {
        final byte[] myFileContent = "HELLO, WORLD!".getBytes();
        final String myFileName = "helloWorldFile.txt";
        final String path = folder.getRoot().getAbsolutePath();

        when(mediaRepository.save(any(Media.class))).thenAnswer(new Answer<Media>() {
            @Override
            public Media answer(InvocationOnMock invocation) throws Throwable {
                return (Media) invocation.getArguments()[0];
            }
        });

        final Media media = mediaService.createNewMedia(myFileContent, MediaType.IMAGE, path, myFileName);
        assertNotNull(media);
        assertEquals(myFileName, media.getUrl());
        assertEquals(MediaType.IMAGE, media.getMediaType());

        verify(mediaRepository).save(any(Media.class));

        final byte[] bytes = Files.readAllBytes(Paths.get(path, myFileName));
        assertEquals("HELLO, WORLD!", new String(bytes));
    }

    @Test
    public void testFindAllByIds() throws Exception {
        final Long id1 = 1L;
        final Long id2 = 2L;

        final Media media1 = new Media();
        media1.setUrl("TEST_URL_1");
        media1.setMediaType(MediaType.IMAGE);

        final Media media2 = new Media();
        media2.setUrl("TEST_URL_2");
        media2.setMediaType(MediaType.VIDEO);

        when(mediaRepository.findOne(id1))
                .thenReturn(media1);
        when(mediaRepository.findOne(id2))
                .thenReturn(media2);

        final Collection<Media> actualMedias = mediaService.findAllByIds(Arrays.asList(id1, id2));
        assertNotNull(actualMedias);
        assertFalse(actualMedias.isEmpty());
        assertThat(actualMedias, Matchers.contains(media1, media2));

    }
}