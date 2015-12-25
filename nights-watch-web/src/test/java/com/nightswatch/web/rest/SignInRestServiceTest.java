package com.nightswatch.web.rest;


import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.user.UserToken;
import com.nightswatch.service.exception.AuthenticationFailedException;
import com.nightswatch.service.exception.DataNotFoundException;
import com.nightswatch.service.user.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SignInRestServiceTest extends AbstractRestServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SignInRestServiceImpl signInRestService;

    @Override
    public Object getRestService() {
        return signInRestService;
    }

    @Test
    public void testAuthenticate() throws Exception {
        final UserToken userToken = new UserToken();
        userToken.setToken("TEST_TOKEN");
        userToken.setUser(new User());
        userToken.getUser().setId(101L);

        when(userService.signIn("test_username", "test_password"))
                .thenReturn(userToken);

        mockMvc.perform(
                post("/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test_username\",\"password\":\"test_password\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is("TEST_TOKEN")))
                .andExpect(jsonPath("$.userId", Matchers.is(101)));

        verify(userService).signIn("test_username", "test_password");
    }

    @Test
    public void testAuthenticationFailed() throws Exception {

        when(userService.signIn("test_username", "test_password"))
                .thenThrow(new AuthenticationFailedException("test_username", "test_password"));

        mockMvc.perform(
                post("/signin")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test_username\",\"password\":\"test_password\"}"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.path", Matchers.is("/signin")))
                .andExpect(jsonPath("$.message", Matchers.allOf(Matchers.containsString("test_username"), Matchers.containsString("test_password"))));

        verify(userService).signIn("test_username", "test_password");
    }

    @Test
    public void testResetPassword() throws Exception {

        mockMvc.perform(
                post("/resetPassword")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test_username\",\"email\":\"test@gmail.com\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", Matchers.notNullValue()))
                .andExpect(jsonPath("$.response", Matchers.is("SUCCESS")));

        verify(userService).resetPassword("test_username", "test@gmail.com");

    }

    @Test
    public void testResetPasswordButUserNotFound() throws Exception {

        doThrow(new DataNotFoundException()).when(userService).resetPassword("test_username", "test@gmail.com");

        mockMvc.perform(
                post("/resetPassword")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test_username\",\"email\":\"test@gmail.com\"}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", Matchers.notNullValue()))
                .andExpect(jsonPath("$.path", Matchers.is("/resetPassword")));

        verify(userService).resetPassword("test_username", "test@gmail.com");

    }
}
