package com.nightswatch.api.rest.user;

import com.nightswatch.api.dto.SignInRequestDto;
import com.nightswatch.api.dto.user.ResetPasswordRequestDto;
import com.nightswatch.api.dto.user.ResetPasswordResponseDto;
import com.nightswatch.api.dto.user.SignInResponseDto;

public interface SignInRestService {

    SignInResponseDto signIn(SignInRequestDto signInRequestDto);

    ResetPasswordResponseDto resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);
}
