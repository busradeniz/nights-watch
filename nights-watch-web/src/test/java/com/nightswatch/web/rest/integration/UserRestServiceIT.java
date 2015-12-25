package com.nightswatch.web.rest.integration;

import com.nightswatch.api.dto.user.SignInRequestDto;
import com.nightswatch.api.dto.user.SignInResponseDto;
import com.nightswatch.api.dto.user.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class UserRestServiceIT extends AbstractIT {

    @Test
    public void testGet() throws Exception {
        final RestTemplate restTemplate = this.getRestTemplate();

        final SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUsername("test");
        signInRequestDto.setPassword("test");

        final SignInResponseDto signInResponseDto = restTemplate.postForObject(this.baseUrl + "/signin", signInRequestDto, SignInResponseDto.class);
        Assert.assertNotNull(signInResponseDto);
        Assert.assertNotNull(signInResponseDto.getToken());

        final RestTemplate secureRestTemplate = this.getSecureTemplate(signInResponseDto.getToken());
        final UserDto userDto = secureRestTemplate.getForObject(this.baseUrl + "/user/1", UserDto.class);
        Assert.assertEquals("test", userDto.getUsername());
        Assert.assertEquals("busradeniz89@gmail.com", userDto.getEmail());
    }

    @Test
    public void testUpdate() throws Exception {

    }
}