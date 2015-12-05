package com.nightswatch.web.rest;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.exception.TokenInvalidException;
import com.nightswatch.service.user.UserTokenService;
import org.springframework.util.StringUtils;

public abstract class AbstractAuthenticatedRestService {

    protected final UserTokenService userTokenService;

    protected AbstractAuthenticatedRestService(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    // TODO Aspect daha guzel bir cozum olacaktir...
    protected User checkAuthorizationToken(final String token) {
        if (StringUtils.isEmpty(token)) {
            throw new TokenInvalidException("null");
        }

        return userTokenService.getUserByToken(token);
    }
}
