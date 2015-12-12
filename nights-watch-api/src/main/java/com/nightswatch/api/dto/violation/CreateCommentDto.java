package com.nightswatch.api.dto.violation;

import java.util.Collection;

public class CreateCommentDto extends AbstractCommentDto {

    private Collection<Long> mediaIds;
    private Long violationId;

    public Collection<Long> getMediaIds() {
        return mediaIds;
    }

    public void setMediaIds(Collection<Long> mediaIds) {
        this.mediaIds = mediaIds;
    }

    public Long getViolationId() {
        return violationId;
    }

    public void setViolationId(Long violationId) {
        this.violationId = violationId;
    }
}
