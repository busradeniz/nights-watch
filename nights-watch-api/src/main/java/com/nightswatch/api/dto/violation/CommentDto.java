package com.nightswatch.api.dto.violation;

import com.nightswatch.api.dto.MediaDto;

import java.util.Collection;

public class CommentDto extends AbstractCommentDto {

    private Long id;
    private String username;
    private Collection<MediaDto> mediaDtos;
    private Long violationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<MediaDto> getMediaDtos() {
        return mediaDtos;
    }

    public void setMediaDtos(Collection<MediaDto> mediaDtos) {
        this.mediaDtos = mediaDtos;
    }

    public Long getViolationId() {
        return violationId;
    }

    public void setViolationId(Long violationId) {
        this.violationId = violationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentDto that = (CommentDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (mediaDtos != null ? !mediaDtos.equals(that.mediaDtos) : that.mediaDtos != null) return false;
        return !(violationId != null ? !violationId.equals(that.violationId) : that.violationId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (mediaDtos != null ? mediaDtos.hashCode() : 0);
        result = 31 * result + (violationId != null ? violationId.hashCode() : 0);
        return result;
    }
}
