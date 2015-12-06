package com.nightswatch.api.dto.violation;

import com.nightswatch.api.dto.EntityDto;
import com.nightswatch.api.dto.MediaDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * DTO object for Reading and Listing operations.
 */
public class ViolationDto extends AbstractViolationDto implements EntityDto {

    private Long id;
    private Date violationDate;
    private Collection<String> tags;
    private Collection<MediaDto> medias;
    private Collection<String> userLikes;
    private Long comments;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getViolationDate() {
        return violationDate;
    }

    public void setViolationDate(Date violationDate) {
        this.violationDate = violationDate;
    }

    public Collection<String> getTags() {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Collection<MediaDto> getMedias() {
        if (medias == null) {
            medias = new ArrayList<>();
        }
        return medias;
    }

    public void setMedias(Collection<MediaDto> medias) {
        this.medias = medias;
    }

    public Collection<String> getUserLikes() {
        if (userLikes == null) {
            userLikes = new ArrayList<>();
        }
        return userLikes;
    }

    public void setUserLikes(Collection<String> userLikes) {
        this.userLikes = userLikes;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }
}
