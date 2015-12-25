package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.user.*;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserRestServiceIT extends AbstractIT {

    @Test
    public void testGet() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        final RestTemplate secureRestTemplate = this.getSecureTemplate(signInResponseDto.getToken());
        final UserDto userDto = secureRestTemplate.getForObject(this.baseUrl + "/user/1", UserDto.class);
        assertEquals("test", userDto.getUsername());
        assertEquals("busradeniz89@gmail.com", userDto.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testChangePassword() throws Exception {

        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = this.getRestTemplate().postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        assertNotNull(signInResponseDto);
        assertNotNull(signInResponseDto.getToken());

        final ChangePasswordRequestDto changePasswordRequestDto = new ChangePasswordRequestDto();
        changePasswordRequestDto.setOldPassword("test");
        changePasswordRequestDto.setNewPassword("test1");

        final ChangePasswordResponseDto changePasswordResponseDto = this.getSecureTemplate(signInResponseDto.getToken()).postForObject(this.baseUrl + "/user/changePassword", changePasswordRequestDto, ChangePasswordResponseDto.class);
        assertNotNull(changePasswordRequestDto);
        assertEquals(ResponseType.SUCCESS, changePasswordResponseDto.getResponse());

        final SignInRequestDto signInRequestDto2 = new SignInRequestDto();
        signInRequestDto2.setUsername("test");
        signInRequestDto2.setPassword("test1");

        final SignInResponseDto signInResponseDto2 = this.getRestTemplate().postForObject(this.baseUrl + "/signin", signInRequestDto2, SignInResponseDto.class);
        assertNotNull(signInResponseDto2);
        assertNotNull(signInResponseDto2.getToken());

        final ChangePasswordRequestDto changePasswordRequestDto2 = new ChangePasswordRequestDto();
        changePasswordRequestDto2.setOldPassword("test1");
        changePasswordRequestDto2.setNewPassword("test");

        final ChangePasswordResponseDto changePasswordResponseDto2 = this.getSecureTemplate(signInResponseDto2.getToken()).postForObject(this.baseUrl + "/user/changePassword", changePasswordRequestDto2, ChangePasswordResponseDto.class);
        assertNotNull(changePasswordResponseDto2);
        assertEquals(ResponseType.SUCCESS, changePasswordResponseDto2.getResponse());

    }
}