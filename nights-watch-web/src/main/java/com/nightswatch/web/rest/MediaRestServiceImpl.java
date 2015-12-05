package com.nightswatch.web.rest;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.api.rest.MediaRestService;
import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.exception.MediaIOException;
import com.nightswatch.service.user.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
        return this.convertMediaDtoFromMedia(media);
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
            final Media newMedia = this.mediaService.createNewMedia(file.getBytes(), mediaType, fullPath, file.getOriginalFilename());
            return this.convertMediaDtoFromMedia(newMedia);
        } catch (IOException e) {
            log.error("Error while creating a new media.", e);
            throw new MediaIOException(file.getName(), e);
        }
    }

    private MediaDto convertMediaDtoFromMedia(final Media media) {
        final MediaDto mediaDto = new MediaDto();
        mediaDto.setId(media.getId());
        mediaDto.setFileName(media.getUrl()); // TODO media.url'i media.fileName olarak refactor et
        // RelativePath = /public/{media_type}/{media.fileName}
        final String relativeMediaPath = "/public/" + media.getMediaType().name().toLowerCase(Locale.ENGLISH) + "/" + media.getUrl();
        final String baseUrl = this.getBaseUrl() + servletContext.getContextPath();
        mediaDto.setUrl(baseUrl + relativeMediaPath);
        return mediaDto;
    }

    private String getBaseUrl() {

        try {
            final HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            final URL url = new URL(httpServletRequest.getRequestURL().toString());
            return url.getProtocol() + "://" + url.getHost() + ":" + url.getPort();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Requested url cannot be parsed.", e);
        }
    }


}
