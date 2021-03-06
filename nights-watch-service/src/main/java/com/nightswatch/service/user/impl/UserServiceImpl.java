package com.nightswatch.service.user.impl;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.user.UserStatusType;
import com.nightswatch.dal.entity.user.UserToken;
import com.nightswatch.dal.repository.user.UserRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.MailingService;
import com.nightswatch.service.exception.AuthenticationFailedException;
import com.nightswatch.service.exception.DataNotFoundException;
import com.nightswatch.service.user.RoleService;
import com.nightswatch.service.user.UserService;
import com.nightswatch.service.user.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleService roleService;
    private final UserTokenService userTokenService;
    private final MailingService mailingService;

    @Autowired
    public UserServiceImpl(UserRepository repository,
                           RoleService roleService,
                           UserTokenService userTokenService,
                           MailingService mailingService) {
        super(repository);
        this.roleService = roleService;
        this.userTokenService = userTokenService;
        this.mailingService = mailingService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserToken signIn(String username, String password) {
        log.debug("Trying to Authenticate for username ({}) and password({}) ", username, password);
        final User user = this.repository.findByUsernameAndPassword(username, password);
        if (user == null) {
            log.error("User was not found for username ({}) and password({}) ", username, password);
            throw new AuthenticationFailedException(username, password);
        }
        log.debug("User({}) is found for username ({}) and password({}) ", user, username, password);
        final String token = UUID.randomUUID().toString();
        final UserToken userToken = userTokenService.createUserToken(token, user);
        log.debug("Token({}) is created for for username ({}) and password({}) ", token, username, password);
        return userToken;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void resetPassword(String username, String email) {
        final User user = this.repository.findByUsernameAndEmail(username, email);
        if (user == null) {
            throw new DataNotFoundException("User was not found for username: " + username + " email: " + email);
        }

        user.setPassword("NW+" + new Random().nextInt(9999));
        this.repository.save(user);
        this.mailingService.sendPasswordResetEmail(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User registerBasicUser(String username, String password, String email) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(roleService.getBasicUserRoles());
        user.setUserStatusType(UserStatusType.ACTIVE);

        this.save(user);
        return user;
    }
}
