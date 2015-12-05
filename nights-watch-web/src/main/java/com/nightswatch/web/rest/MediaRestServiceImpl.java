package com.nightswatch.web.rest;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.rest.MediaRestService;
import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.exception.MediaIOException;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.web.util.MediaDtoConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Locale;

@RestController
@RequestMapping("/media")
public class MediaRestServiceImpl extends AbstractAuthenticatedRestService implements MediaRestService {

    private static final Logger log = LoggerFactory.getLogger(MediaRestServiceImpl.class);

    private final MediaService mediaService;
    private final ServletContext servletContext;

    @Autowired
    public MediaRestServiceImpl(final MediaService mediaService,
                                final UserTokenService userTokenService,
                                final ServletContext servletContext) {
        super(userTokenService);
        this.mediaService = mediaService;
        this.servletContext = servletContext;
    }

    /**
     * Finds entities with given id and returns its information as DTO
     *
     * @param id existing unique entity id
     * @return existing entity as AccountDTO
     */
    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public MediaDto get(@PathVariable Long id, @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final Media media = this.mediaService.findOne(id);
        log.debug("Media({}) is found for given id({}).", media, id);
        return MediaDtoConversionUtils.convertToDTO(media);
    }

    /**
     * Uploads an image file and creates a Media for given data
     *
     * @param file  that contains image
     * @param token authentication token
     * @return created media information
     */
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public MediaDto upload(@RequestParam("file") MultipartFile file, @RequestParam("mediaType") MediaType mediaType, @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);

        try {
            final ServletContextResource servletContextResource = new ServletContextResource(servletContext, "/public/" + mediaType.name().toLowerCase(Locale.ENGLISH));
            final String fullPath = servletContextResource.getFile().getPath();
            log.debug("Uploaded file is going to ve saved '{}'", fullPath);
            final Media newMedia = this.mediaService.createNewMedia(file.getBytes(), mediaType, fullPath, file.getOriginalFilename());
            log.debug("Upload is completed and a new media entity is created. Entity: {}", newMedia);
            return MediaDtoConversionUtils.convertToDTO(newMedia);
        } catch (IOException e) {
            log.error("Error while creating a new media.", e);
            throw new MediaIOException(file.getName(), e);
        }
    }


}
