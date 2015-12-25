package com.nightswatch.web.util;

import com.nightswatch.api.dto.user.UserDto;
import com.nightswatch.dal.entity.user.User;

public final class UserDtoConversionUtils {
    private UserDtoConversionUtils() {
    }

    public static UserDto convert(final User user) {
        final UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setFullName(user.getFullName());
        userDto.setBirthday(user.getBirthday());
        userDto.setGenderTypeDto(EnumDtoConversionUtils.convert(user.getGenderType()));
        userDto.setBio(user.getBio());
        userDto.setRoles(RoleDtoConversionUtls.convert(user.getRoles()));
        if (user.getMedia() != null) {
            userDto.setPhoto(MediaDtoConversionUtils.convert(user.getMedia()));
        }
        return userDto;
    }
}
