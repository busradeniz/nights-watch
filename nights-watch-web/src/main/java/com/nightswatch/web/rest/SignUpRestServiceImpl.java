package com.nightswatch.web.rest;

import com.nightswatch.api.dto.user.UserDto;
import com.nightswatch.api.rest.user.SingUpRestService;
import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
public class SignUpRestServiceImpl implements SingUpRestService {

    private static final Logger log = LoggerFactory.getLogger(SignUpRestServiceImpl.class);

    private final UserService userService;

    @Autowired
    public SignUpRestServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDto signUp(@RequestBody UserDto userDto) {
        log.debug("Registering Basic user for {}", userDto);
        final User user = this.userService.registerBasicUser(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());

        log.debug("User ({}) is created for {}", user, userDto);
        final UserDto createdUserDto = new UserDto();
        createdUserDto.setUsername(user.getUsername());
        createdUserDto.setEmail(user.getEmail());
        final Collection<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());
        }
        createdUserDto.setRoles(roles);
        log.debug("UserDto ({}) is going to be returned for {}", createdUserDto, userDto);
        return createdUserDto;
    }
}
