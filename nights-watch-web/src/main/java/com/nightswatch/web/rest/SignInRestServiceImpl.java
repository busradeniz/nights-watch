package com.nightswatch.web.rest;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.user.ResetPasswordRequestDto;
import com.nightswatch.api.dto.user.ResetPasswordResponseDto;
import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.rest.user.SignInRestService;
import com.nightswatch.dal.entity.user.UserToken;
import com.nightswatch.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignInRestServiceImpl implements SignInRestService {

    private static final Logger log = LoggerFactory.getLogger(SignInRestServiceImpl.class);

    private final UserService userService;

    @Autowired
    public SignInRestServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @ResponseBody
    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    @Override
    public SignInResponseDto signIn(@RequestBody SignInRequestDto signInRequestDto) {
        log.debug("Trying to signIn for authenticationDto {}", signInRequestDto);
        final UserToken userToken = userService.signIn(signInRequestDto.getUsername(), signInRequestDto.getPassword());
        log.debug("Token ({}) is created for signInRequestDto {}", userToken, signInRequestDto);
        final SignInResponseDto signInResponseDto = new SignInResponseDto();
        signInResponseDto.setMessage("Giriş işlemi başarılı şekilde sonuçlandı.");
        signInResponseDto.setToken(userToken.getToken());
        signInResponseDto.setUserId(userToken.getUser().getId());
        signInResponseDto.setResponse(ResponseType.SUCCESS);
        log.debug("SignInResponseDto ({}) is going to be returned for signInRequestDto {}", signInResponseDto, signInRequestDto);
        return signInResponseDto;
    }

    @Override
    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResetPasswordResponseDto resetPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        log.debug("Trying to reset password for {}", resetPasswordRequestDto);
        userService.resetPassword(resetPasswordRequestDto.getUsername(), resetPasswordRequestDto.getEmail());
        final ResetPasswordResponseDto resetPasswordResponseDto = new ResetPasswordResponseDto();
        resetPasswordResponseDto.setMessage("Kullanıcının şifresi başarıyla resetlendi");
        resetPasswordResponseDto.setResponse(ResponseType.SUCCESS);
        return resetPasswordResponseDto;
    }

}
