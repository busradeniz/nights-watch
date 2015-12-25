package com.nightswatch.api.dto.user;

import com.nightswatch.api.dto.GenericResponseDto;

public class SignInResponseDto extends GenericResponseDto {

    private String token;

    private Long userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SignInResponseDto{" +
                "token='" + token + '\'' +
                ", userId=" + userId +
                '}';
    }
}
