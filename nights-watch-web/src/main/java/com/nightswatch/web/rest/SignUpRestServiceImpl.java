package com.nightswatch.web.rest;

import com.nightswatch.api.dto.user.BasicUserDto;
import com.nightswatch.api.dto.user.UserDto;
import com.nightswatch.api.rest.user.SingUpRestService;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.user.UserService;
import com.nightswatch.web.util.UserDtoConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public UserDto signUp(@RequestBody BasicUserDto basicUserDto) {
        log.debug("Registering Basic user for {}", basicUserDto);
        final User user = this.userService.registerBasicUser(basicUserDto.getUsername(), basicUserDto.getPassword(), basicUserDto.getEmail());

        log.debug("User ({}) is created for {}", user, basicUserDto);
        final UserDto userDto = UserDtoConversionUtils.convert(user);
        log.debug("UserDto ({}) is going to be returned for {}", userDto, basicUserDto);
        return userDto;
    }
}
