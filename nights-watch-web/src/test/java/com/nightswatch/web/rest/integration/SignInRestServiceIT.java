package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.user.*;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class SignInRestServiceIT extends AbstractIT {


    @Test
    public void testAuthenticate() throws Exception {
        final RestTemplate restTemplate = super.getRestTemplate();

        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        Assert.assertNotNull(signInResponseDto);
        Assert.assertNotNull(signInResponseDto.getToken());
        Assert.assertNotNull(signInResponseDto.getUserId());
    }

    @Test
    public void testAuthenticationFailed() throws Exception {
        final RestTemplate restTemplate = super.getRestTemplate();

        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("wrong_username");
        signInRequestDto.setPassword("wrong_password");

        try {
            restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, UserDto.class);
            Assert.fail("Authentication should fail.");
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.UNAUTHORIZED, e.getStatusCode());
        }
    }

    @Test
    @DirtiesContext
    public void testResetPassword() throws Exception {
        final ResetPasswordRequestDto resetPasswordRequestDto = new ResetPasswordRequestDto();
        resetPasswordRequestDto.setUsername("test");
        resetPasswordRequestDto.setEmail("busradeniz89@gmail.com");

        final ResetPasswordResponseDto resetPasswordResponseDto = getRestTemplate().postForObject(this.baseUrl + "/resetPassword", resetPasswordRequestDto, ResetPasswordResponseDto.class);
        Assert.assertNotNull(resetPasswordRequestDto);
        Assert.assertEquals(ResponseType.SUCCESS, resetPasswordResponseDto.getResponse());
    }

    @Test
    public void testResetPasswordButUserNotFound() throws Exception {
        final ResetPasswordRequestDto resetPasswordRequestDto = new ResetPasswordRequestDto();
        resetPasswordRequestDto.setUsername("test2");
        resetPasswordRequestDto.setEmail("test2@gmail.com");

        try {
            getRestTemplate().postForObject(this.baseUrl + "/resetPassword", resetPasswordRequestDto, ResetPasswordResponseDto.class);
            Assert.fail("ResetPassword should fail.");
        } catch (HttpClientErrorException e) {
            Assert.assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }

    }
}