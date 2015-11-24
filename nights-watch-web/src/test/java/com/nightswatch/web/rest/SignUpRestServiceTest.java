package com.nightswatch.web.rest;


import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.user.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SignUpRestServiceTest extends AbstractRestServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SignUpRestServiceImpl signUpRestService;

    @Override
    public Object getRestService() {
        return signUpRestService;
    }

    @Test
    public void testSignUp() throws Exception {
        final User user = new User();
        user.setUsername("test_username");
        user.setPassword("test_password");
        user.setEmail("test@gmail.com");
        final Role role = new Role();
        role.setRoleName("BASIC");
        user.setRoles(Collections.singletonList(role));

        when(userService.registerBasicUser("test_username", "test_password", "test@gmail.com"))
                .thenReturn(user);

        mockMvc.perform(
                post("/signup")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"test_username\",\"password\":\"test_password\", \"email\":\"test@gmail.com\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", Matchers.is("test_username")))
                .andExpect(jsonPath("$.email", Matchers.is("test@gmail.com")));

        verify(userService).registerBasicUser("test_username", "test_password", "test@gmail.com");
    }
}
