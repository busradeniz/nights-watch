package com.nightswatch.web.rest;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.exception.TokenInvalidException;
import com.nightswatch.service.user.UserTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public abstract class AbstractAuthenticatedRestService {

    private static final Logger log = LoggerFactory.getLogger(AbstractAuthenticatedRestService.class);

    protected final UserTokenService userTokenService;

    protected AbstractAuthenticatedRestService(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    // TODO Aspect daha guzel bir cozum olacaktir...
    protected User checkAuthorizationToken(final String token) {
        log.debug("Authorization is checking for token {}", token);
        if (StringUtils.isEmpty(token)) {
            log.warn("Token is null or empty. Please check your Authorization header.");
            throw new TokenInvalidException("null");
        }
        final User user = userTokenService.getUserByToken(token);
        log.debug("User ({}) is found for given token({}).", user, token);
        return user;
    }
}
