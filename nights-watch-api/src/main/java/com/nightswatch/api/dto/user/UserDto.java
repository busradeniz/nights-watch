package com.nightswatch.api.dto.user;

import com.nightswatch.api.dto.EntityDto;

import java.util.Collection;

public class UserDto implements EntityDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<String> roles;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (!username.equals(userDto.username)) return false;
        if (!email.equals(userDto.email)) return false;
        return roles.equals(userDto.roles);

    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + roles.hashCode();
        return result;
    }
}
