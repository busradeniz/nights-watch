package com.nightswatch.api.dto.user;

import com.nightswatch.api.dto.GenericResponseDto;

public class SignInResponseDto extends GenericResponseDto {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SignInResponseDto{" +
                "token='" + token + '\'' +
                "super='" + super.toString() + '\'' +
                '}';
    }
}
