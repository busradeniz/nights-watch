package com.nightswatch.api.dto.violation;

import java.util.Date;

public class UserLikeDto {

    private Long id;
    private Long violationId;
    private String username;
    private Date likeDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViolationId() {
        return violationId;
    }

    public void setViolationId(Long violationId) {
        this.violationId = violationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(Date likeDate) {
        this.likeDate = likeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLikeDto that = (UserLikeDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (violationId != null ? !violationId.equals(that.violationId) : that.violationId != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        return !(likeDate != null ? !likeDate.equals(that.likeDate) : that.likeDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (violationId != null ? violationId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (likeDate != null ? likeDate.hashCode() : 0);
        return result;
    }
}
