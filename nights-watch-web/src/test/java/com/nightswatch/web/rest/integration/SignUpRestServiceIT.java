package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.user.BasicUserDto;
import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.user.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

public class SignUpRestServiceIT extends AbstractIT {

    @Test
    public void testSignUpAndThenSignIn() throws Exception {
        final RestTemplate restTemplate = super.getRestTemplate();

        final BasicUserDto userRegister = new BasicUserDto();
        userRegister.setUsername("test_1");
        userRegister.setPassword("test_1");
        userRegister.setEmail("test_1@gmail.com");

        final UserDto createdUser = restTemplate.postForObject(this.baseUrl + "/signup", userRegister, UserDto.class);
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(userRegister.getUsername(), createdUser.getUsername());
        Assert.assertEquals(userRegister.getEmail(), createdUser.getEmail());
        Assert.assertNotNull(createdUser.getRoles());
        Assert.assertFalse(createdUser.getRoles().isEmpty());
        Assert.assertEquals("BASIC", createdUser.getRoles().iterator().next());


        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test_1");
        signInRequestDto.setPassword("test_1");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        Assert.assertNotNull(signInResponseDto);
        Assert.assertNotNull(signInResponseDto.getToken());
    }

    @Test
    public void testSignUpWithSameUsername() throws Exception {
        final RestTemplate restTemplate = super.getRestTemplate();

        final BasicUserDto userRegister = new BasicUserDto();
        userRegister.setUsername("test_2");
        userRegister.setPassword("test_2");
        userRegister.setEmail("test_2@gmail.com");

        final UserDto createdUser1 = restTemplate.postForObject(this.baseUrl + "/signup", userRegister, UserDto.class);
        Assert.assertNotNull(createdUser1);
        Assert.assertEquals(userRegister.getUsername(), createdUser1.getUsername());
        Assert.assertEquals(userRegister.getEmail(), createdUser1.getEmail());
        Assert.assertNotNull(createdUser1.getRoles());
        Assert.assertFalse(createdUser1.getRoles().isEmpty());
        Assert.assertEquals("BASIC", createdUser1.getRoles().iterator().next());

        try {
            restTemplate.postForObject(this.baseUrl + "/signup", userRegister, UserDto.class);
        } catch (HttpStatusCodeException e) {
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }

        //DataIntegrityViolationException
    }
}