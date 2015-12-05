package com.nightswatch.service;

import com.nightswatch.dal.entity.Media;
import com.nightswatch.dal.entity.MediaType;

public interface MediaService extends CrudService<Media> {

    Media createNewMedia(final byte[] file, final MediaType mediaType, final String path, final String fileName);
}
