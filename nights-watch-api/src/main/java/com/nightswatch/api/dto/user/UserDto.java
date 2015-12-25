package com.nightswatch.api.dto.user;

import com.nightswatch.api.dto.EntityDto;
import com.nightswatch.api.dto.MediaDto;

import java.util.Collection;
import java.util.Date;

public class UserDto implements EntityDto {

    private Long id;
    private String username;
    private String email;
    private String fullName;
    private Date birthday;
    private GenderTypeDto genderTypeDto;
    private String bio;
    private Collection<String> roles;
    private MediaDto photo;

    @Override
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public GenderTypeDto getGenderTypeDto() {
        return genderTypeDto;
    }

    public void setGenderTypeDto(GenderTypeDto genderTypeDto) {
        this.genderTypeDto = genderTypeDto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    public MediaDto getPhoto() {
        return photo;
    }

    public void setPhoto(MediaDto photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != null ? !id.equals(userDto.id) : userDto.id != null) return false;
        if (username != null ? !username.equals(userDto.username) : userDto.username != null) return false;
        return !(email != null ? !email.equals(userDto.email) : userDto.email != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
