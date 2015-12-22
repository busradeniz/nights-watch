package com.nightswatch.api.dto.violation;

import java.util.Collection;

public class UpdateCommentDto extends AbstractCommentDto {

    private Collection<Long> mediaIds;

    public Collection<Long> getMediaIds() {
        return mediaIds;
    }

    public void setMediaIds(Collection<Long> mediaIds) {
        this.mediaIds = mediaIds;
    }
}
