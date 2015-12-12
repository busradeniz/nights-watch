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
    private String owner;
    private Collection<String> tags;
    private Collection<MediaDto> medias;
    private Collection<String> userLikes;
    private Integer commentCount;
    private Integer userLikeCount;
    private Integer userWatchCount;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUserLikeCount() {
        return userLikeCount;
    }

    public void setUserLikeCount(Integer userLikeCount) {
        this.userLikeCount = userLikeCount;
    }

    public Integer getUserWatchCount() {
        return userWatchCount;
    }

    public void setUserWatchCount(Integer userWatchCount) {
        this.userWatchCount = userWatchCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViolationDto that = (ViolationDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (violationDate != null ? !violationDate.equals(that.violationDate) : that.violationDate != null)
            return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (medias != null ? !medias.equals(that.medias) : that.medias != null) return false;
        if (userLikes != null ? !userLikes.equals(that.userLikes) : that.userLikes != null) return false;
        if (commentCount != null ? !commentCount.equals(that.commentCount) : that.commentCount != null) return false;
        if (userLikeCount != null ? !userLikeCount.equals(that.userLikeCount) : that.userLikeCount != null)
            return false;
        return !(userWatchCount != null ? !userWatchCount.equals(that.userWatchCount) : that.userWatchCount != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (violationDate != null ? violationDate.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (medias != null ? medias.hashCode() : 0);
        result = 31 * result + (userLikes != null ? userLikes.hashCode() : 0);
        result = 31 * result + (commentCount != null ? commentCount.hashCode() : 0);
        result = 31 * result + (userLikeCount != null ? userLikeCount.hashCode() : 0);
        result = 31 * result + (userWatchCount != null ? userWatchCount.hashCode() : 0);
        return result;
    }
}
