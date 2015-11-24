package com.nightswatch.service.user;

import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.CrudService;

public interface UserService extends CrudService<User> {

    String signIn(final String username, final String password);

    void resetPassword(String username, String email);

    User registerBasicUser(String username, String password, String email);
}
