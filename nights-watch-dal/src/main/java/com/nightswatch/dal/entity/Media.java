package com.nightswatch.dal.entity;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "MEDIA")
@Access(AccessType.FIELD)
public class Media extends AbstractEntity {

    @Column(name = "URL")
    private String url;

    @Column(name = "MEDIA_TYPE")
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
