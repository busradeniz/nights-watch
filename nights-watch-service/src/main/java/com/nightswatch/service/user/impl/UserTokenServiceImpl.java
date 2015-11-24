package com.nightswatch.service.user.impl;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.dal.entity.user.UserToken;
import com.nightswatch.dal.repository.user.UserTokenRepository;
import com.nightswatch.service.AbstractService;
import com.nightswatch.service.exception.TokenInvalidException;
import com.nightswatch.service.user.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class UserTokenServiceImpl extends AbstractService<UserToken, UserTokenRepository> implements UserTokenService {

    @Autowired
    public UserTokenServiceImpl(UserTokenRepository repository) {
        super(repository);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserToken createUserToken(final String token, final User user) {
        final UserToken userToken = new UserToken();
        userToken.setToken(token);
        userToken.setUser(user);
        userToken.setCreateDate(new Date());
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 2);
        // Geçerlilik süresi 2 gün
        userToken.setExpiryDate(instance.getTime());
        return this.save(userToken);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByToken(final String token) {
        final UserToken byToken = this.repository.findByToken(token);
        if (byToken == null) {
            throw new TokenInvalidException(token);
        }

        return byToken.getUser();
    }
}
