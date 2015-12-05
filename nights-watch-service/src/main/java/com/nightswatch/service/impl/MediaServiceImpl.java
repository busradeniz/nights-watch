package com.nightswatch.service.impl;

import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;
import com.nightswatch.dal.repository.MediaRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.exception.MediaIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class MediaServiceImpl extends AbstractService<Media, MediaRepository> implements MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

    @Autowired
    public MediaServiceImpl(MediaRepository repository) {
        super(repository);
    }

    @Override
    public Media createNewMedia(byte[] file, MediaType mediaType, String fullPath, String fileName) {
        try {
            if (Files.notExists(Paths.get(fullPath))) {
                Files.createDirectories(Paths.get(fullPath));
            }
            Files.write(Paths.get(fullPath, fileName), file, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            final Media media = new Media();
            media.setMediaType(mediaType);
            media.setUrl(fileName); // TODO refactor media.url --> media.fileName
            return this.save(media);
        } catch (IOException e) {
            log.error("Error while creating media.", e);
            throw new MediaIOException(fileName, e);
        }
    }
}
