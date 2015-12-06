package com.nightswatch.api.dto.violation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class CreateViolationRequestDto extends AbstractViolationDto implements Serializable {

    private Collection<Long> tags;
    private Collection<Long> medias;

    public Collection<Long> getTags() {
        if (tags == null) {
            this.tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(Collection<Long> tags) {
        this.tags = tags;
    }

    public Collection<Long> getMedias() {
        if (medias == null) {
            medias = new ArrayList<>();
        }
        return medias;
    }

    public void setMedias(Collection<Long> medias) {
        this.medias = medias;
    }
}
