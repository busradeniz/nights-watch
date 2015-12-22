package com.nightswatch.api.dto.violation;

public class UserWatchDto {

    private Long id;
    private Long violationId;
    private String username;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWatchDto that = (UserWatchDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (violationId != null ? !violationId.equals(that.violationId) : that.violationId != null) return false;
        return !(username != null ? !username.equals(that.username) : that.username != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (violationId != null ? violationId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
