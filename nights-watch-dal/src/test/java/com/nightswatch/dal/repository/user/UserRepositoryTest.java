package com.nightswatch.dal.repository.user;

import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.repository.AbstractRepositoryTest;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

public class UserRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() throws Exception {
        final User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("1q2w3e");
        user.setUsername("test");
        final Role role = new Role();
        role.setRoleName("TEST_ROLE");
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getId());
        Assert.assertNotNull(user.getRoles());
        Assert.assertNotNull(user.getRoles().iterator().next());
        Assert.assertNotNull(user.getRoles().iterator().next().getId());
    }

    @Test
    public void testFindByUsernameAndPassword() throws Exception {
        final String username = "test_user";
        final String password = "1q2w3e";

        final User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword(password);
        user.setUsername(username);
        final Role role = new Role();
        role.setRoleName("TEST_ROLE");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        final User dbUser = userRepository.findByUsernameAndPassword(username, password);

        Assert.assertEquals(user, dbUser);
    }

    @Test
    public void testFindByUsernameAndPasswordWithoutData() throws Exception {
        final String username = "test_user_12345";
        final String password = "1q2w3e";
        final User dbUser = userRepository.findByUsernameAndPassword(username, password);

        Assert.assertNull(dbUser);
    }

    @Test
    public void testFindByUsernameAndEmail() throws Exception {
        final String username = "test_user_2";
        final String email = "test2@gmail.com";

        final User user = new User();
        user.setEmail(email);
        user.setPassword("1q2w3e");
        user.setUsername(username);
        final Role role = new Role();
        role.setRoleName("TEST_ROLE_2");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        final User dbUser = userRepository.findByUsernameAndEmail(username, email);

        Assert.assertEquals(user, dbUser);

    }
}