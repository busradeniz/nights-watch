package com.nightswatch.api.dto.user;

public class ResetPasswordRequestDto {

    private String username;
    private String email;

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

    @Override
    public String toString() {
        return "ResetPasswordRequestDto{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
