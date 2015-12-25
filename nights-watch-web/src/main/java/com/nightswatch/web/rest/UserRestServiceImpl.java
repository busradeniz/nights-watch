package com.nightswatch.web.rest;

import com.nightswatch.api.dto.ResponseType;
import com.nightswatch.api.dto.user.ChangePasswordRequestDto;
import com.nightswatch.api.dto.user.ChangePasswordResponseDto;
import com.nightswatch.api.dto.user.UserDto;
import com.nightswatch.api.rest.user.UserRestService;
import com.nightswatch.dal.entity.user.Role;
import com.nightswatch.dal.entity.user.User;
import com.nightswatch.service.MediaService;
import com.nightswatch.service.exception.WrongPasswordException;
import com.nightswatch.service.user.RoleService;
import com.nightswatch.service.user.UserService;
import com.nightswatch.service.user.UserTokenService;
import com.nightswatch.web.util.EnumDtoConversionUtils;
import com.nightswatch.web.util.UserDtoConversionUtils;
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
    private final MediaService mediaService;
    private final RoleService roleService;

    @Autowired
    public UserRestServiceImpl(final UserTokenService userTokenService,
                               final UserService userService,
                               final MediaService mediaService,
                               final RoleService roleService) {
        super(userTokenService);
        this.userService = userService;
        this.mediaService = mediaService;
        this.roleService = roleService;
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
        return UserDtoConversionUtils.convert(user);
    }

    /**
     * Updates entity information with data from userDto. id and entityId in the dto must match.
     *
     * @param id      existing unique entity id
     * @param userDto entity information will be updated according to this data
     * @return updated version of entity as entity dto
     */
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto, @RequestHeader(name = "Authorization", required = false) final String token) {
        this.checkAuthorizationToken(token);

        final User user = this.userService.findOne(id);
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setGenderType(EnumDtoConversionUtils.convert(userDto.getGenderTypeDto()));
        user.setBirthday(userDto.getBirthday());
        user.setBio(userDto.getBio());
        if (userDto.getPhoto() != null && userDto.getPhoto().getId() != null) {
            user.setMedia(this.mediaService.findOne(userDto.getId()));
        }

        if (userDto.getRoles() != null) {
            final Collection<Role> roles = new ArrayList<>();
            for (String roleName : userDto.getRoles()) {
                roles.add(this.roleService.findByRoleName(roleName));
            }
            user.setRoles(roles);
        }

        this.userService.save(user);

        return UserDtoConversionUtils.convert(user);
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    public ChangePasswordResponseDto changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto,
                                                    @RequestHeader(name = "Authorization", required = false) final String token) {
        final User user = this.checkAuthorizationToken(token);
        if (!user.getPassword().equals(changePasswordRequestDto.getOldPassword())) {
            throw new WrongPasswordException(changePasswordRequestDto.getOldPassword());
        }

        user.setPassword(changePasswordRequestDto.getNewPassword());
        this.userService.save(user);

        final ChangePasswordResponseDto changePasswordResponseDto = new ChangePasswordResponseDto();
        changePasswordResponseDto.setResponse(ResponseType.SUCCESS);
        return changePasswordResponseDto;
    }

}
