package com.nightswatch.web.rest;

import com.nightswatch.api.dto.user.UserDto;
import com.nightswatch.api.rest.user.UserRestService;
import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.user.UserService;
import com.nightswatch.service.user.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/user")
@Transactional(propagation = Propagation.REQUIRED)
public class UserRestServiceImpl extends AbstractAuthenticatedRestService implements UserRestService {

    private final UserService userService;

    @Autowired
    public UserRestServiceImpl(UserTokenService userTokenService, UserService userService) {
        super(userTokenService);
        this.userService = userService;
    }

    /**
     * Finds entities with given id and returns its information as DTO
     *
     * @param id existing unique entity id
     * @return existing entity as UserDto
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public UserDto get(@PathVariable Long id, @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);
        final User user = userService.findOne(id);
        return createUserDtoFromUser(user);
    }

    /**
     * Updates entity information with data from entityDto. id and entityId in the dto must match.
     *
     * @param id        existing unique entity id
     * @param entityDto entity information will be updated according to this data
     * @return updated version of entity as entity dto
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public UserDto update(@PathVariable Long id, @RequestBody UserDto entityDto, @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);

        final User dbUser = this.userService.findOne(id);
        dbUser.setUsername(entityDto.getUsername());
        dbUser.setPassword(entityDto.getPassword());
        dbUser.setEmail(entityDto.getEmail());
        this.userService.save(dbUser);

        return this.createUserDtoFromUser(dbUser);
    }

    // TODO DTO <--> Entity dönüşümü için servisler yazılacak
    private UserDto createUserDtoFromUser(User user) {
        final UserDto createdUserDto = new UserDto();
        createdUserDto.setUsername(user.getUsername());
        createdUserDto.setEmail(user.getEmail());
        final Collection<String> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());
        }
        createdUserDto.setRoles(roles);
        return createdUserDto;
    }

}
