package com.nightswatch.dal.repository.user;

import com.nightswatch.dal.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsernameAndEmail(String username, String email);
}
