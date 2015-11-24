package com.nightswatch.dal.repository.user;

import com.nightswatch.dal.entity.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByToken(String token);
}
