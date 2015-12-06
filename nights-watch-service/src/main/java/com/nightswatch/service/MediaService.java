package com.nightswatch.service;

import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;

import java.util.Collection;

public interface MediaService extends CrudService<Media> {

    Media createNewMedia(final byte[] file, final MediaType mediaType, final String path, final String fileName);

    Collection<Media> findAllByIds(Collection<Long> ids);
}
