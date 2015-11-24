package com.nightswatch.service.user;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.user.UserToken;
import com.nightswatch.service.CrudService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserTokenService extends CrudService<UserToken> {
    @Transactional(propagation = Propagation.REQUIRED)
    UserToken createUserToken(String token, User user);

    @Transactional(propagation = Propagation.SUPPORTS)
    User getUserByToken(String token);
}
