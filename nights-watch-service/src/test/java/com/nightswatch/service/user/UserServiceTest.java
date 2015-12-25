package com.nightswatch.service.user;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.user.UserToken;
import com.nightswatch.dal.repository.user.UserRepository;
import com.nightswatch.service.exception.AuthenticationFailedException;
import com.nightswatch.service.user.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTokenService userTokenService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testAuthenticate() throws Exception {
        final String username = "testUsername";
        final String password = "testPassword";

        final User expectedUser = new User();

        final UserToken expectedUserToken = new UserToken();
        expectedUserToken.setUser(expectedUser);
        expectedUserToken.setToken("TEST_TOKEN");

        when(userRepository.findByUsernameAndPassword(username, password))
                .thenReturn(expectedUser);
        when(userTokenService.createUserToken(anyString(), eq(expectedUser)))
                .thenReturn(expectedUserToken);

        final UserToken userToken = userService.signIn(username, password);
        Assert.assertEquals(expectedUserToken, userToken);
    }

    @Test(expected = AuthenticationFailedException.class)
    public void testAuthenticateWithoutUser() throws Exception {
        final String username = "testUsername";
        final String password = "testPassword";

        when(userRepository.findByUsernameAndPassword(username, password))
                .thenReturn(null);

        userService.signIn(username, password);
    }
}