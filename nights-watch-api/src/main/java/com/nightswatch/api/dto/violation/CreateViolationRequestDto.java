package com.nightswatch.api.dto.violation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class CreateViolationRequestDto extends AbstractViolationDto implements Serializable {

    private Collection<String> tags;
    private Collection<Long> medias;

    public Collection<String> getTags() {
        if (tags == null) {
            this.tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(Collection<String> tags) {
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
