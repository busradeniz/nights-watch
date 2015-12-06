package com.nightswatch.web.util;

import com.nightswatch.api.dto.MediaDto;
import com.nightswatch.dal.entity.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public final class MediaDtoConversionUtils {

    private static final Logger log = LoggerFactory.getLogger(MediaDtoConversionUtils.class);

    private MediaDtoConversionUtils() {
    }

    public static MediaDto convert(final Media media) {
        log.debug("Conversion is started for media({})", media);
        final MediaDto mediaDto = new MediaDto();
        mediaDto.setId(media.getId());
        mediaDto.setFileName(media.getUrl()); // TODO media.url'i media.fileName olarak refactor et
        final String relativeMediaPath = "/public/" + media.getMediaType().name().toLowerCase(Locale.ENGLISH) + "/" + media.getUrl();
        final String aliveUrl = SpringMvcUtils.getBaseUrl() + relativeMediaPath;
        log.debug("A new url({}) is created for Media({}).", aliveUrl, media);
        mediaDto.setUrl(aliveUrl);
        log.debug("Conversion is finished for media({}). Result Dto({})", media, mediaDto);
        return mediaDto;
    }

    public static Collection<MediaDto> convert(final Collection<Media> medias) {
        final Collection<MediaDto> mediaDtos = new ArrayList<>();
        for (Media media : medias) {
            mediaDtos.add(MediaDtoConversionUtils.convert(media));
        }

        return mediaDtos;
    }
}
