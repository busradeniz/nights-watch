package com.nightswatch.service.impl;

import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;
import com.nightswatch.dal.repository.MediaRepository;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
}